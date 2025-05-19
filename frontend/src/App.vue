<template>
  <div id="app">
    <LivroForm />
    <LivroList />
  </div>
</template>

<script>
import LivroForm from './views/LivroForm.vue';
import LivroList from './views/LivroList.vue';

export default {
  name: 'App',
  components: {
    LivroForm,
    LivroList
  }
};
</script>
