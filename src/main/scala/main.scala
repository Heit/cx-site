package ru.circumflex.site

import ru.circumflex.core._
import ru.circumflex.freemarker.FreemarkerHelper
import ru.ciridiri.{Page, CiriDiri}
import ru.circumflex.md.Markdown
import java.text.SimpleDateFormat
import java.util.Date
import java.io.File
import org.apache.commons.io.FileUtils._
import java.lang.{String, StringBuilder}
import org.apache.commons.io.filefilter.{TrueFileFilter, DirectoryFileFilter}

class MainRouter extends RequestRouter
    with FreemarkerHelper {
  'host := header.getOrElse("Host", "")
  'currentYear := new SimpleDateFormat("yyyy").format(new Date)
  'sitemap := Page.findByUri("/nav") // read sitemap

  new CiriDiri { // let ciridiri handle the rest

    override def onFound(page: Page) = "toc" := new TOC(page.toHtml)

    override def onUpdate(page: Page) = {
      val parent = (new File(page.path)).getParentFile()
      val navFile = new File(parent, "nav.md")
      val data = parent.listFiles()
          .toList
          .sort((f1, f2) => f1.getName < f2.getName)
          .flatMap {
        case f if f.isDirectory => Some(new File(f, "index.md"))
        case f if f.getName.endsWith(".md") => Some(f)
        case _ => None
      }.flatMap(f => Page.findByPath(f.getAbsolutePath))
          .filter(p => p.title != "")
          .map(p => "* " + p.toString)
          .mkString("\n")

      writeStringToFile(navFile, data, "UTF-8")
    }

    override def onDelete(page: Page) = {
      val parent = (new File(page.path)).getParentFile()
      val navFile = new File(parent, "nav.md")
      val data: String = readFileToString(navFile, "UTF-8")
          .split("\n")
          .filter(l => l != "* " + page.toString)
          .mkString("\n")
      writeStringToFile(navFile, data, "UTF-8")
    }

  }

  // Markdown Live
  get("/.mdwn") = ftl("/mdwn.ftl")
  post("/.mdwn") = Markdown(param('md))
  get("/.md-cheatsheet") =
      if (isXhr)
        Page.findByUriOrEmpty("/.md-cheatsheet").toHtml
      else rewrite("/.md-cheatsheet.html")

  get("+/") = redirect(uri(1) + "/index.html")
  get("+") = redirect(uri(1) + ".html")

}

object TOC {
  val rHeadings = "<h(\\d)\\s*(id\\s*=\\s*(\"|')(.*?)\\3)?\\s*>(.*?)</h\\1>".r
}

class TOC(val html: String) {
  case class Heading(level: Int, id: String, body: String) {
    def toHtml: String =
      if (id == null) "<span>" + body + "</span>"
      else "<a href=\"#" + id + "\">" + body + "</a>"

    override def toString = toHtml
  }
  val headings: Seq[Heading] = TOC.rHeadings.findAllIn(html)
      .matchData
      .map(m => new Heading(m.group(1).toInt, m.group(4), m.group(5)))
      .toList
  val toHtml: String = if (headings.size == 0) "" else {
    val sb = new StringBuilder
    def startList(l: Int) = sb.append("  " * l + "<li><ul>\n")
    def endList(l: Int) = sb.append("  " * (l - 1) + "</ul></li>\n")
    def add(l: Int, h: Heading) = sb.append("  " * l + "<li>" + h.toString + "</li>\n")
    def formList(level: Int, index: Int): Unit = if (index < 0 || index >= headings.size) {
      if (level > 1) {
        endList(level)
        formList(level - 1, index)
      }
    } else {
      val h = headings(index)
      if (level < h.level) {
        startList(level)
        formList(level + 1, index)
      } else if (level > h.level) {
        endList(level)
        formList(level - 1, index)
      } else {
        add(level, h)
        formList(level, index + 1)
      }
    }
    formList(1, 0)
    "<ul>\n" + sb.toString + "</ul>"
  }
}

class Nav() {
  val toHtml: String = {

    def recursiveListFiles(f: File): List[File] = {
      val list:List[File] = Nil;
      for (f <- (new File("src/main/webapp/pages")).listFiles ) {list = list ::: List(f)}
      list;
    }
    val files = recursiveListFiles(new File(Page.contentDir))
//    files.filter( f => """.*\.md$""".r.findFirstIn(f.getName).isDefined)
  }
}




