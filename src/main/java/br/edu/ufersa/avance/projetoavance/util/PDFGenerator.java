package br.edu.ufersa.avance.projetoAvance.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PDFGenerator {

    public static void gerarRelatorio(
            String tituloRelatorio,
            List<String> cabecalhos,
            List<Map<String, String>> dados,
            String caminhoArquivo) throws DocumentException, FileNotFoundException {

        Document document = new Document(PageSize.A4.rotate()); // Paisagem para melhor visualização
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));

        document.open();

        // Adicionar título
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph(tituloRelatorio, tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // Adicionar data e hora
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Paragraph data = new Paragraph("Gerado em: " + dataHora, dataFont);
        data.setAlignment(Element.ALIGN_RIGHT);
        data.setSpacingAfter(20);
        document.add(data);

        // Criar tabela
        PdfPTable table = new PdfPTable(cabecalhos.size());
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Configurar cabeçalho
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        for (String cabecalho : cabecalhos) {
            PdfPCell cell = new PdfPCell(new Phrase(cabecalho, headerFont));
            cell.setBackgroundColor(new BaseColor(70, 130, 180)); // Azul steel
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Adicionar dados
        Font dataCellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        for (Map<String, String> linha : dados) {
            for (String cabecalho : cabecalhos) {
                String valor = linha.getOrDefault(cabecalho, "");
                table.addCell(new Phrase(valor, dataCellFont));
            }
        }

        document.add(table);
        document.close();
    }

    public static String getCaminhoRelatorio(String nomeRelatorio) {
        String diretorio = System.getProperty("user.home") + File.separator + "relatorios_escola_musica";
        new File(diretorio).mkdirs(); // Cria o diretório se não existir

        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return diretorio + File.separator + nomeRelatorio + "_" + dataHora + ".pdf";
    }
}