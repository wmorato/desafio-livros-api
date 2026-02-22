import { test, expect } from '@playwright/test'
import fs from 'fs'

// Cria a pasta screenshots se não existir
const screenshotsDir = 'screenshots/livro-list'
if (!fs.existsSync(screenshotsDir)) {
  fs.mkdirSync(screenshotsDir, { recursive: true })
}

test('pesquisa livro 888 e espera encontrar "Wilson Morato"', async ({ page }) => {
  // 1. Acessa a página de login
  await page.goto('http://localhost')
  await page.screenshot({ path: `${screenshotsDir}/step1-login.png` })

  // 2. Faz login como admin
  await page.fill('input[placeholder="Usuário"]', 'admin')
  await page.fill('input[placeholder="Senha"]', '123456')
  await page.screenshot({ path: `${screenshotsDir}/step2-preenchido-login.png` })
  await page.click('button[type="submit"]')

  // 3. Aguarda redirecionamento para a home
  await expect(page).toHaveURL('http://localhost/')
  await page.screenshot({ path: `${screenshotsDir}/step3-home.png` })

  // 4. Navega para a lista de livros
  await page.click('text=Consultar Livros')

  // 5. Aguarda a página de lista de livros
  await expect(page).toHaveURL(/\/livros\/lista/)
  await page.screenshot({ path: `${screenshotsDir}/step4-livros-lista.png` })

  // 6. Pesquisa pelo livro 888
  await page.fill('input[placeholder="Pesquisar por título..."]', '888')
  await page.screenshot({ path: `${screenshotsDir}/step5-pesquisa-888.png` })

  // 7. Espera que "Wilson Morato" apareça na tabela
  await expect(page.locator('text=Wilson Morato')).toBeVisible()
  await page.screenshot({ path: `${screenshotsDir}/step6-wilson-morato.png` })

  // 8. Pausa para inspeção manual (opcional)
  await page.pause()
})