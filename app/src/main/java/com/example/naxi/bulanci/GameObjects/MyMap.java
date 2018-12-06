package com.example.naxi.bulanci.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import com.example.naxi.bulanci.GameView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


class MyImage
{
    public int PositionX;
    public int PositionY;
    public Bitmap image;
}

public class MyMap
{

    private Bitmap back;
    private ArrayList<MyImage> background = new ArrayList<MyImage>();
    private ArrayList<MyImage> foreground = new ArrayList<MyImage>();
    public ArrayList<Rect> collisions = new ArrayList<Rect>();
    private Map<String, Bitmap> images = new HashMap<String, Bitmap>();
    private GameView GameView;

    public MyMap(String mapname, GameView gw)
    {
        GameView = gw;
        loadMap(mapname);
    }

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

            myXMLRead(parser, file);

        }catch(XmlPullParserException e)
        {
            Log.d("NWM", "XmlPullParserException");
        }catch(IOException e)
        {
            Log.d("NWM", "IOException");
        }

    }

    private void myXMLRead(XmlPullParser parser, String folder) throws XmlPullParserException, IOException
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
                        while(!((tagname.equals("background"))&&(eventType==XmlPullParser.END_TAG)))
                        {
                            parser.next();
                            tagname = parser.getName();
                            eventType = parser.getEventType();
                            if (tagname.equals("x"))
                            {
                                bck.PositionX = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("y"))
                            {
                                bck.PositionY = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("image"))
                            {
                                bck.image = images.get(parser.nextText());//bck.image = parser.nextText();
                            }

                        }
                        background.add(bck);
                    }
                    else

                    if (tagname.equals("foreground"))
                    {
                        MyImage bck = new MyImage();
                        while(!((tagname.equals("foreground"))&&(eventType==XmlPullParser.END_TAG)))
                        {
                            parser.next();
                            tagname = parser.getName();
                            eventType = parser.getEventType();
                            if (tagname.equals("x"))
                            {
                                bck.PositionX = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("y"))
                            {
                                bck.PositionY = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("image"))
                            {
                                bck.image = images.get(parser.nextText());//bck.image = parser.nextText();
                                int k = 5+5;
                            }

                        }
                        foreground.add(bck);
                    }
                    else
                    if (tagname.equals("ground"))
                    {
                        while(!((tagname.equals("ground"))&&(eventType==XmlPullParser.END_TAG)))
                        {
                            parser.next();
                            tagname = parser.getName();
                            eventType = parser.getEventType();

                            if (tagname.equals("image"))
                            {

                                //bck.image = parser.nextText();
                                back = images.get(parser.nextText());
                            }

                        }
                    }

                    else
                    if (tagname.equals("collision"))
                    {
                        Rect collision = new Rect();
                        while(!((tagname.equals("collision"))&&(eventType==XmlPullParser.END_TAG)))
                        {
                            parser.next();
                            tagname = parser.getName();
                            eventType = parser.getEventType();
                            if (tagname.equals("x1"))
                            {
                                collision.left = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("y1"))
                            {
                                collision.top = Integer.parseInt(parser.nextText());
                            }else
                            if (tagname.equals("x2"))
                            {
                                collision.right = Integer.parseInt(parser.nextText());
                            }
                            else
                            if (tagname.equals("y2"))
                            {
                                collision.bottom = Integer.parseInt(parser.nextText());
                            }

                        }
                        collisions.add(collision);
                    }
                    else
                    if (tagname.equals("load"))
                    {
                        loadImage(parser.nextText(),folder);
                    }

                    break;
                }
            }


            eventType = parser.next();
        }

    }

    private void loadImage(String image, String folder)
    {
        String imagePath = Environment.getExternalStorageDirectory()+"/bulanci/"+folder+"/"+image;
        BitmapFactory.Options options = new BitmapFactory.Options();

        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inScaled = false;

        Bitmap img = BitmapFactory.decodeFile(imagePath, options);

        images.put(image,img);
    }


    public void DrawBackground(Canvas canvas)
    {
        canvas.drawBitmap(back, null, new Rect((int)(0),(int)(0),(int)(1100*GameView.ScalingX), (int)(640*GameView.ScalingY)), null);


        for(MyImage i : background)
        {
            canvas.drawBitmap(i.image, null, new Rect((int)(i.PositionX*GameView.ScalingX),(int)(i.PositionY*GameView.ScalingY),(int)((i.PositionX+i.image.getWidth())*GameView.ScalingX), (int)((i.PositionY+i.image.getHeight())*GameView.ScalingY)), null);



            /*
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(25);

            canvas.drawText(i.PositionX+"  "+i.PositionY+"  "+(i.PositionX+i.image.getWidth())+"  "+(i.PositionY+i.image.getHeight()),(int)(i.PositionX*GameView.ScalingX),(int)(i.PositionY*GameView.ScalingY),paint);
*/
        }

        /*
        for(Rect col : collisions)
        {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(new Rect((int)(col.left*GameView.ScalingX),(int)(col.top*GameView.ScalingY) , (int)(col.right*GameView.ScalingX), (int)(col.bottom*GameView.ScalingY)), paint);
        }
        */
    }

    public void DrawForeground(Canvas canvas)
    {
        for(MyImage i : foreground)
        {
            canvas.drawBitmap(i.image, null, new Rect((int)(i.PositionX*GameView.ScalingX),(int)(i.PositionY*GameView.ScalingY),(int)((i.PositionX+i.image.getWidth())*GameView.ScalingX), (int)((i.PositionY+i.image.getHeight())*GameView.ScalingY)), null);

        }
    }
}
