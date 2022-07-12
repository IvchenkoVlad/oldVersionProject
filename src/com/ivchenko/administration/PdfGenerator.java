package com.ivchenko.administration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	public void generateReceivedTicket(List<Parcel> parcels) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Received_sticker.pdf"));

			Font mailBoxFont = new Font(FontFamily.TIMES_ROMAN, 200.0f, Font.BOLD, BaseColor.BLACK);
			Font generalFont = new Font(FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);
			document.open();
			for (int i = 0; i < parcels.size(); i++) {
				Paragraph lineOne = new Paragraph("Parcel details:", generalFont);
				Paragraph lineTwo = new Paragraph("#" + parcels.get(i).getUserFK(), mailBoxFont);
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
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void TestGenerator() {
		Document document = new Document();
	      try
	      {
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Received_sticker.pdf"));
	         
	         Font mailBoxFont =new Font(FontFamily.TIMES_ROMAN,200.0f,Font.BOLD,BaseColor.BLACK);
	         Font generalFont =new Font(FontFamily.TIMES_ROMAN,30.0f,Font.BOLD,BaseColor.BLACK);
	         document.open();
	         
	         Paragraph lineOne = new Paragraph("Parcel details:", generalFont); 
	         Paragraph lineTwo = new Paragraph("#60", mailBoxFont);
	         Paragraph lineThree = new Paragraph("Tracking:", generalFont);
	         Paragraph lineFour = new Paragraph("1Z7845A8458D343423\nDate received:\n05-06-2019", generalFont);
	         
	         lineOne.setAlignment(Element.ALIGN_CENTER);
	         lineTwo.setAlignment(Element.ALIGN_CENTER);
	         lineThree.setAlignment(Element.ALIGN_CENTER);
	         lineFour.setAlignment(Element.ALIGN_CENTER);

	         document.add(lineOne);
	         document.add(lineTwo);
	         document.add(lineThree);
	         document.add(lineFour);
	         document.newPage();
	         document.add(lineOne);
	         document.add(lineTwo);
	         document.add(lineThree);
	         document.add(lineFour);
	         document.close();
	         writer.close();
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	}
}
