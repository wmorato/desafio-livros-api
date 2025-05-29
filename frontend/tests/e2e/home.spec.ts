import { test, expect } from '@playwright/test'

test('navega para Consultar Livros após login', async ({ page }) => {
  console.log('1. Acessando a página de login...')
  await page.goto('http://localhost')

  console.log('2. Preenchendo usuário...')
  await page.fill('input[placeholder="Usuário"]', 'admin')

  console.log('3. Preenchendo senha...')
  await page.fill('input[placeholder="Senha"]', '123456')

  console.log('4. Clicando no botão Entrar...')
  await page.click('button[type="submit"]')

  // Aguarda redirecionamento para a home
  console.log('5. Verificando se está na home...')
  await expect(page).toHaveURL('http://localhost/')

  // Clica no card "Consultar Livros"
  console.log('6. Clicando no card Consultar Livros...')
  await page.click('text=Consultar Livros')

  // Verifica se está na rota de lista de livros
  console.log('7. Verificando se está na página de lista de livros...')
  await expect(page).toHaveURL(/\/livros\/lista/)

  // Pausa para inspeção manual
  console.log('8. Pausando para inspeção manual...')
  await page.pause()
})