<template>
  <div class="modal-overlay">
    <div class="modal-box">
      <h2 style="font-size: 1.2rem; margin-bottom: 1rem;">Envie sua mensagem</h2>
      <label for="tipo">Tipo:</label>
      <select id="tipo" v-model="tipo" style="margin-bottom: 8px;">
        <option value="Sugestão">Sugestão</option>
        <option value="Crítica">Crítica</option>
        <option value="Elogio">Elogio</option>
      </select>
      <label for="msg">Mensagem:</label>
      <textarea id="msg" v-model="mensagem" rows="3" style="width:100%;margin-bottom:12px;" placeholder="Digite aqui..."></textarea>
      <div style="display:flex;justify-content: flex-end; gap: 10px;">
        <button class="logout-btn" @click="enviarSugestao">Enviar</button>
        <button class="logout-btn" style="background:#f5f5f5;color:#444;" @click="fechar">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SugestaoModal",
  data() {
    return {
      tipo: "Sugestão",
      mensagem: ""
    };
  },
  methods: {
    enviarSugestao() {
      alert("Sugestão enviada!");
      this.fechar();
    },
    fechar() {
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
.modal-box {
  background: #fff;
  padding: 2rem 1.3rem 1.3rem 1.3rem;
  border-radius: 14px;
  max-width: 340px;
  width: 100%;
  box-shadow: 0 6px 30px #0002;
  display: flex;
  flex-direction: column;
}
</style>
