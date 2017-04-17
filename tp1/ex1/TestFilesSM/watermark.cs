/*****************************************************************************************
 * Centro de Cálculo do Instituto Superior de Engenharia de Lisboa - CCISEL              *
 *****************************************************************************************
 * Curso de formação em .NET 2.0, 3.0 e 3.5                                              *
 * 24 a 28 de Março de 2008 - WRC - Cúria					                             *
 *                                                                         © 2008 CCISEL *
 *****************************************************************************************/

using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;

using System.Drawing;
using System.Drawing.Imaging;
using System.Drawing.Text;

//#region Factory

//public class WaterMarkHandlerFactory : IHttpHandlerFactory 
//{
//    private WaterMarkHandler instance;

//    public IHttpHandler GetHandler(HttpContext context, string requestType, string url, string pathTranslated) 
//    {
//        if(instance == null)
//          instance = new WaterMarkHandler("sample");
//        return instance;
//    }

//    public void ReleaseHandler(IHttpHandler handler) 
//    {
//    }
//}

//#endregion

public class WaterMarkHandler : IHttpHandler
{
    private string text;

    #region Geração da imagem com WaterMark

    private Image transform(Image img)
    {
        Graphics g;
        using (g = Graphics.FromImage(img))
        {
            g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
            g.TextRenderingHint = TextRenderingHint.AntiAlias;

            int FontSize = img.Width / 5;

            SizeF size = g.MeasureString(text,
            new Font("Arial Black", FontSize, FontStyle.Bold, GraphicsUnit.Pixel));

            int Y = (int)((img.Height - size.Height) / 2);
            int X = (int)((img.Width - size.Width) / 2);

            //g.TranslateTransform(img.Width, -10);  //Fudged value
            //g.RotateTransform(35);

            g.DrawString(text,
            new Font("Arial Black", FontSize, FontStyle.Bold, GraphicsUnit.Pixel),
            new SolidBrush(Color.FromArgb(150, Color.LightGray)), X, Y);
        }
        return img;
    }

    #endregion

    public WaterMarkHandler(string waterMarkText)
    {
        text = waterMarkText;
    }

    public WaterMarkHandler()
    {
        text = "Sample";
    }

    #region Interface IHttpHandler

    public void ProcessRequest(HttpContext context)
    {
        context.Response.ContentType = "image/jpeg";
        string filename = "images/" + context.Request.QueryString["Image"];
        Image img;
        using (img = Image.FromFile(context.Server.MapPath(filename)))
        {
            transform(img);
            img.Save(context.Response.OutputStream, ImageFormat.Jpeg);
        }
    }

    public bool IsReusable
    {
        get { return true; }
    }

    #endregion
}
