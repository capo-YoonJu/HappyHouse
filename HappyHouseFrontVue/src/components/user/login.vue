<template>
  <div>
    <div class="login-container">
      <h2>로그인</h2>
      <b-alert variant="danger" dismissible fade 
        v-model="showDismissibleAlert">
        아이디 또는 비밀번호를 다시 확인하세요.
      </b-alert>
      <b-form class="login-form" @submit="onSubmit">
        <b-form-group
          id="id-group"
          label="아이디"
          label-for="id"
        >
          <b-form-input
            id="id-input"
            v-model="user.userId"
            type="text"
            placeholder="아이디"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="password-group"
          label="비밀번호"
          label-for="password"
        >
          <b-form-input
            id="password-input"
            v-model="user.userPassword"
            type="password"
            placeholder="비밀번호"
            required
          ></b-form-input>
        </b-form-group>

        <b-button type="submit" variant="primary">로그인 하기</b-button>
      </b-form>
    </div>
  </div>
</template>

<script>
  import { login } from "@/api/user.js";
  import { mapActions } from "vuex";
  export default {
    data() {
      return {
        showDismissibleAlert: false,
        user: {
          userId: null,
          userPassword: null
        },
      }
    },
    methods: {
      ...mapActions("userStore", ["getUserInfo"]),
      onSubmit(event) {
        event.preventDefault();
        login(this.user,
          ({ data }) => {
            if (data.message === "success") {
              let token = data["access-token"];
              sessionStorage.setItem("access-token", token);
              this.getUserInfo();
              this.showDismissibleAlert = false;
              alert("로그인 되었습니다.");
              this.$router.push({ name: "Home" });
            } else {
              this.showDismissibleAlert = true;
              alert("로그인에 실패하였습니다.");
            }
          },
          (error) => {
            console.log(error);
            this.showDismissibleAlert = true;
            alert("로그인에 실패하였습니다.");
          },
        );
      }
    }
  }
</script>

<style scoped>
  .login-container {
    margin: 2rem auto;
    max-width: 20rem;
  }
  h2,.alert-danger,.form-group,.btn {
    font-weight: bold;
  }
  .alert-danger {
    padding: 1rem 3rem 1rem 1rem;
    text-align: left;
    color: #D32F2F;
  }
  form {
    margin-top: 1rem;
  }
  .form-group {
    margin-bottom: 1rem;
    text-align: left;
  }
  .form-control {
    border: 1px solid #D7E2EB;
    background-color: #FBFBFD;
  }
  .btn {
    margin-top: 1rem;
    width: 100%;
  }
</style>