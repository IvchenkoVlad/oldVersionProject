package com.ivchenko.administration;

import java.io.*;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
// Document Object
import com.itextpdf.text.Document;
//For adding content into PDF document
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

/**
 * Servlet implementation class PdfGeneratorServlet
 */
@WebServlet("/PdfGeneratorServlet")
public class PdfGeneratorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//invoked from doGet method to create PDF through servlet 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Set content type to application / pdf
        //browser will open the document only if this is set
        response.setContentType("application/pdf");
        //Get the output stream for writing PDF object        
        OutputStream out=response.getOutputStream();
        try {
            Document document = new Document();
            Font mailBoxFont = new Font(FontFamily.TIMES_ROMAN, 200.0f, Font.BOLD, BaseColor.BLACK);
			Font generalFont = new Font(FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);
            /* Basic PDF Creation inside servlet */
            PdfWriter.getInstance(document, out);
//            @SuppressWarnings("unchecked")
			@SuppressWarnings("unchecked")
			List<Parcel> parcels = (List<Parcel>) request.getAttribute("parcelList");
			int index = parcels.size();
            document.open();
            document.newPage();
            for (int i = 0; i < index; i++) {
				Paragraph lineOne = new Paragraph("Parcel details:", generalFont);
				Paragraph lineTwo = new Paragraph("#" + parcels.get(i).getStatus(), mailBoxFont);
				Paragraph lineThree = new Paragraph("Tracking:", generalFont);
				Paragraph lineFour = new Paragraph(parcels.get(i).getTrackingNumber() + "\nDate received:\n" + new Date(), generalFont);

				lineOne.setAlignment(Element.ALIGN_CENTER);
				lineTwo.setAlignment(Element.ALIGN_CENTER);
				lineThree.setAlignment(Element.ALIGN_CENTER);
				lineFour.setAlignment(Element.ALIGN_CENTER);

				document.add(lineOne);
				document.add(lineTwo);
				document.add(lineThree);
				document.add(lineFour);
				document.newPage();
			}
            document.close();
        }
                catch (DocumentException exc){
                throw new IOException(exc.getMessage());
                }
        finally {            
            out.close();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
        rDispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
        rDispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "This Servlet Generates PDF Using iText Library";
    }
}
