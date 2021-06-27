package com.example.rssreader.ui;

import android.util.Xml;

import com.prof.rssparser.Article;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;

public class RssParser {
    public Article parse(Reader in, Article article) throws XmlPullParserException, IOException {
        if (!isParse(article)) {
            return article;
        }
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in);
            return parseArticle(parser, article);
        } finally {
            in.close();
        }
    }

    private boolean isParse(Article article) {
        if (article.getLink() == null) {
            return true;
        }
        if (article.getTitle() == null) {
            return true;
        }
        if (article.getLink().isEmpty()) {
            return true;
        }
        return article.getTitle().isEmpty();
    }

    private Article parseArticle(XmlPullParser parser, Article article) throws IOException, XmlPullParserException {
        while(parser.next() != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            if (name == null){
                continue;
            }
            switch (name){
                case "blockquote":
                    for(int i = 0; i < parser.getAttributeCount(); i++)
                    {
                        switch (parser.getAttributeName(i)) {
                            case "cite":
                                article.setLink(parser.getAttributeValue(i));
                                article.setSourceUrl(parser.getAttributeValue(i));
                                continue;
                            case "title":
                                article.setTitle(parser.getAttributeValue(i));
                                article.setSourceName(parser.getAttributeValue(i));
                                continue;
                        }
                    }
                    continue;
                case "img":
                    for(int i = 0; i < parser.getAttributeCount(); i++)
                    {
                        switch (parser.getAttributeName(i)) {
                            case "src":
                                article.setImage(parser.getAttributeValue(i));
                                continue;
                        }
                    }
                    if (parser.getAttributeCount() != 4){
                        continue;
                    }
                    while(parser.next() != XmlPullParser.END_DOCUMENT) {
                        String text = parser.getText();
                        if (text == null) {
                            continue;
                        }
                        if (text.equals("")) {
                            continue;
                        }
                        article.setDescription(text);
                        return article;
                    }
                    continue;
                default:
                    continue;
            }
        }
        return article;
    }
}
