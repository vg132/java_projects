package ass7;
/*
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import java.io.OutputStream;

import javax.media.jai.JAI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.PNGEncodeParam;
*/
/**
 * Image creation servlet.
 */
public class ImageCenter //extends HttpServlet
{
	/**
	 * Gets the text from the user and appends it to a image 
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	/*
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		BufferedImage bi= new BufferedImage(160, 120, BufferedImage.TYPE_INT_RGB);
		Graphics g=bi.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0,0,160,100);
		g.setColor(Color.YELLOW);
		for(int i=0;i<20;i++)
		{
			g.drawLine(0,40+i,160,40+i);
			g.drawLine(50+i,0,50+i,100);
		}
		g.drawString(request.getParameter("text"),10,115);
		response.setContentType("image/png");
		exportPNG(bi,response.getOutputStream());
	}*/

	/**
	 * Convert a java image to a PNG and stream the result out on the stream.
	 * 
	 * @param image The image to convert.
	 * @param output The stream to which the result will be written.
	 */
	/*
	public void exportPNG(Image image, OutputStream output)
	{
		try
		{
			ParameterBlock pb= new ParameterBlock();
			pb.addSource(image);
			pb.add(DataBuffer.TYPE_BYTE);
			PNGEncodeParam.RGB encodeParam=new PNGEncodeParam.RGB();
			ImageEncoder encode=ImageCodec.createImageEncoder("PNG", output, encodeParam);
			encode.encode(JAI.create("format", pb));
		}
		catch (IOException io)
		{
			System.out.println("Anka");
		}
	}*/
}