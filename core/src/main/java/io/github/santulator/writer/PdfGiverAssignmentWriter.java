/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import io.github.santulator.core.CoreConstants;
import io.github.santulator.core.I18nBundleProvider;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.GiverAssignment;
import org.apache.commons.lang3.StringUtils;

import java.awt.Color;
import java.io.OutputStream;
import java.net.URL;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PdfGiverAssignmentWriter implements GiverAssignmentWriter {
    public static final String FORMAT_SUFFIX = ".pdf";

    private static final String KEY_PHRASE = "results.phrase";

    private static final String HEADER_IMAGE = "/images/header.png";

    private static final String FONT_NAME = "Times-Roman";

    private static final int FONT_SIZE_MAIN = 16;

    private static final int FONT_SIZE_GIFT_RECEIVER = 20;

    private static final float IMAGE_SCALE_PERCENTAGE = 30;

    private final URL headerImage;

    private final String phrase;

    @Inject
    public PdfGiverAssignmentWriter(final I18nBundleProvider provider) {
        this.headerImage = PdfGiverAssignmentWriter.class.getResource(HEADER_IMAGE);

        this.phrase = provider.bundle().getString(KEY_PHRASE);
    }

    @Override
    public void writeGiverAssignment(final String name, final GiverAssignment assignment, final OutputStream out, final String password) {
        try {
            Document document = new Document(PageSize.A5);
            PdfWriter writer = PdfWriter.getInstance(document, out);

            if (StringUtils.isNotBlank(password)) {
                byte[] bytes = password.getBytes(CoreConstants.CHARSET);

                writer.setEncryption(bytes, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            }
            writer.createXmpMetadata();

            Image header = Image.getInstance(headerImage);

            header.setAlignment(Element.ALIGN_CENTER);
            header.scalePercent(IMAGE_SCALE_PERCENTAGE);

            document.open();
            document.add(header);
            addParagraph(document, assignment.getFrom().getName(), FONT_SIZE_MAIN, Font.BOLDITALIC, Color.BLACK);
            addParagraph(document, phrase, FONT_SIZE_MAIN, Font.ITALIC, Color.BLACK);
            addParagraph(document, assignment.getTo().getName(), FONT_SIZE_GIFT_RECEIVER, Font.BOLDITALIC, Color.RED);
            document.close();
        } catch (Exception e) {
            throw new SantaException(String.format("Unable to create PDF '%s'", name), e);
        }
    }

    private void addParagraph(final Document document, final String text, final int size, final int style, final Color colour) {
        Font font = FontFactory.getFont(FONT_NAME, size, style, colour);
        Paragraph paragraph = new Paragraph(text, font);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
    }

    @Override
    public String getFormatSuffix() {
        return FORMAT_SUFFIX;
    }
}
