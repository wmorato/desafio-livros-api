import { test, expect } from '@playwright/test'

test('login com usuário admin', async ({ page }) => {
  console.log('1. Acessando a página de login...')
  await page.goto('http://localhost')

  console.log('2. Preenchendo usuário...')
  await page.fill('input[placeholder="Usuário"]', 'admin')

  console.log('3. Preenchendo senha...')
  await page.fill('input[placeholder="Senha"]', '123456')

  console.log('4. Clicando no botão Entrar...')
  await page.click('button[type="submit"]')

  // Aguarda redirecionamento para a home (ajuste conforme sua rota)
  console.log('5. Verificando se redirecionou para a home...')
  await expect(page).toHaveURL('http://localhost/')

  // Verifica se algum elemento da home aparece (ajuste conforme seu layout)
  console.log('6. Verificando se elemento da home está visível...')
  await expect(page.locator('text=Bem-vindo')).toBeVisible()

  // Pausa para visualização manual
  console.log('7. Pausando para inspeção manual...')
  //await page.pause()


  
})