package com.example.naxi.bulanci.GameObjects;

import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


class MyImage
{
    public int PositionX;
    public int PositionY;
    public String image;
}

public class Mapa
{

    private MyImage back = new MyImage();
    private ArrayList<MyImage> background = new ArrayList<MyImage>();
    private ArrayList<MyImage> foreground = new ArrayList<MyImage>();
    public ArrayList<Rect> collisions = new ArrayList<Rect>();

    public void loadMap(String file) //XML
    {
        File externalStorage = Environment.getExternalStorageDirectory();
        File mapFile = new File(externalStorage.getAbsolutePath()+"/bulanci/"+file+"/mapa.xml");


        XmlPullParserFactory parserFactory;
        try {

            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = new FileInputStream(mapFile);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is, null);

            myXMLRead(parser);

        }catch(XmlPullParserException e)
        {
            Log.d("NWM", "XmlPullParserException");
        }catch(IOException e)
        {
            Log.d("NWM", "IOException");
        }

    }

    private void myXMLRead(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.getEventType();
        int i = 0;
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            String tagname;

            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                {
                    tagname = parser.getName();
                    if (tagname.equals("background"))
                    {
                        MyImage bck = new MyImage();
                        parser.next(); //X StartTag
                        bck.PositionX = Integer.parseInt(parser.nextText());
                        parser.next(); //X EndTag
                        parser.next(); //Y StartTag
                        bck.PositionY = Integer.parseInt(parser.nextText());
                        parser.next(); //Y EndTag
                        parser.next(); //Image StartTag
                        bck.image = parser.nextText();
                        parser.next(); //Image EndTag
                        background.add(bck);
                    }
                    break;
                }
            }


            eventType = parser.next();
        }

    }
}
