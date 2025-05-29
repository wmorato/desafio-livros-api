import fs from "fs";
import path from "path";
import { Document, Packer, Paragraph, TextRun, ImageRun } from "docx";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const screenshotsDir = path.join(__dirname, "../../screenshots/livro-list");
const steps = [
  { desc: "1. Página de login", file: "step1-login.png" },
  { desc: "2. Login preenchido", file: "step2-preenchido-login.png" },
  { desc: "3. Home", file: "step3-home.png" },
  { desc: "4. Lista de livros", file: "step4-livros-lista.png" },
  { desc: "5. Pesquisa 888", file: "step5-pesquisa-888.png" },
  { desc: "6. Resultado Wilson Morato", file: "step6-wilson-morato.png" },
];

// Corrigido: documento com estrutura mínima válida
const doc = new Document({
  sections: [],
});

const children = [];

for (const step of steps) {
  children.push(
    new Paragraph({ children: [new TextRun({ text: step.desc, bold: true })] })
  );

  const imgPath = path.join(screenshotsDir, step.file);
  if (fs.existsSync(imgPath)) {
    children.push(
      new Paragraph({
        children: [
          new ImageRun({
            data: fs.readFileSync(imgPath),
            transformation: {
              width: 500,
              height: 300,
            },
          }),
        ],
      })
    );
  } else {
    children.push(new Paragraph("Imagem não encontrada"));
  }

  children.push(new Paragraph(""));
}

// Corrigido: adicionando seção corretamente
doc.addSection({ children });

Packer.toBuffer(doc).then((buffer) => {
  fs.writeFileSync(path.join(__dirname, "relatorio-livro-list.docx"), buffer);
  console.log("Arquivo Word gerado: relatorio-livro-list.docx");
});
