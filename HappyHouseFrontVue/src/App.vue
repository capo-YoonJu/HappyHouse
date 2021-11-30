<template>
  <div id="app">
    <nav-bar></nav-bar>
    <router-view/>
    <footer-container></footer-container>
  </div>
</template>

<script>
  import NavBar from '@/components/NavBar.vue';
  import FooterContainer from '@/components/FooterContainer.vue';
  export default {
    components: {
      NavBar, FooterContainer
    },
  };
</script>

<style>
#app {
  font-family: "Inter", 'NotoSansKR', "Helvetica Neue", Helvetica, Arial, "맑은 고딕", malgun gothic, "돋움", Dotum, sans-serif, "Apple Color Emoji", "Noto Color Emoji";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding-top: 56px;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
