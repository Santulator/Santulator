/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.GiverAssignment;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class PdfGiverAssignmentWriter implements GiverAssignmentWriter {
    public static final String FORMAT_SUFFIX = ".pdf";

    private final URL headerImage;

    private final String password;

    private final String phrase;

    @Inject
    public PdfGiverAssignmentWriter(
        @Named("header.image") final String headerImage,
        @Named("password") final String password,
        @Named("phrase") final String phrase) {
        this.headerImage = PdfGiverAssignmentWriter.class.getResource(headerImage);
        this.password = password;
        this.phrase = phrase;
    }


    @Override
    public void writeGiverAssignment(final String name, final GiverAssignment assignment, final OutputStream out) {
        try {
            Document document = new Document(PageSize.A5.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, out);

            if (StringUtils.isNotBlank(password)) {
                byte[] bytes = password.getBytes(Charset.forName("UTF-8"));

                writer.setEncryption(bytes, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            }
            writer.createXmpMetadata();

            Image header = Image.getInstance(headerImage);

            document.open();
            document.add(header);
            addParagraph(document, assignment.getFrom().getName(), 16, Font.BOLDITALIC, Color.BLACK);
            addParagraph(document, phrase, 16, Font.ITALIC, Color.BLACK);
            addParagraph(document, assignment.getTo().getName(), 20, Font.BOLDITALIC, Color.RED);
            document.close();
        } catch (Exception e) {
            throw new SantaException(String.format("Unable to create PDF '%s'", name), e);
        }
    }

    private void addParagraph(final Document document, final String text, final int size, final int style, final Color colour) throws DocumentException {
        Font font = FontFactory.getFont("Times-Roman", size, style, colour);
        Paragraph paragraph = new Paragraph(text, font);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
    }

    @Override
    public String getFormatSuffix() {
        return FORMAT_SUFFIX;
    }
}
