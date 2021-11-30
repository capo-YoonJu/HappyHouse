<template>
  <div>
    <div class="signup-container">
      <h2>회원가입</h2>
      <b-form class="signup-form" @submit="onSubmit">
        <b-form-group
          id="id-group"
          label="아이디"
          label-for="id"
          description="알파벳과 숫자로 이루어진 4~16자리"
          valid-feedback="사용가능한 아이디입니다!"
          :invalid-feedback="isValidId"
          :state="stateId"
        >
          <b-form-input
            id="id-input"
            v-model="user.userId"
            type="text"
            placeholder="아이디"
            required
            :state="stateId"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="password-group"
          label="비밀번호"
          label-for="password"
          description="알파벳과 숫자로 이루어진 4~16자리"
          valid-feedback="사용가능한 비밀번호입니다!"
          :invalid-feedback="isValidPassword"
          :state="statePassword"
        >
          <b-form-input
            id="password-input"
            v-model="user.userPassword"
            type="password"
            placeholder="비밀번호"
            required
            :state="statePassword"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="name-group"
          label="이름"
          label-for="name"
          :invalid-feedback="isValidName"
          :state="stateName"
        >
          <b-form-input
            id="name-input"
            v-model="user.userName"
            type="text"
            placeholder="이름"
            required
            :state="stateName"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="address-group"
          label="주소"
          label-for="address"
          :invalid-feedback="isValidAddress"
          :state="stateAddress"
        >
          <b-form-input
            id="address-input"
            v-model="user.userAddress"
            type="text"
            placeholder="주소"
            required
            :state="stateAddress"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="tel-group"
          label="휴대폰 번호"
          label-for="tel"
          :invalid-feedback="isValidTel"
          :state="stateTel"
        >
          <b-form-input
            id="tel-input"
            v-model="user.userTel"
            type="tel"
            placeholder="휴대폰 번호"
            required
            :state="stateTel"
          ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary">회원가입 하기</b-button>
      </b-form>
    </div>
  </div>
</template>

<script>
  import { idCheck, registerUser } from "@/api/user.js";
  const regId = /^[A-Za-z0-9]{4,12}$/;
  const regPassword = /^[A-Za-z0-9]{4,16}$/;
  const regName = /^[가-힣A-Za-z]{2,20}$/;
  const regAddress = /^[가-힣0-9]{2,30}$/;
  const regTel = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
  const regNumber = /[^0-9]/g;
  export default {
    computed: {
      stateId() {
        return !this.isDuplicated && regId.test(this.user.userId);
      },
      isValidId() {
        if(this.user.userId == "") {
          return "아이디를 입력해주세요.";
        }
        if (this.isDuplicatedID()) {
          return "이미 사용 중인 아이디입니다.";
        }
        return "아이디을 확인해주세요.";
      },
      statePassword() {
        return this.user.userPassword != null && regPassword.test(this.user.userPassword);
      },
      isValidPassword() {
        if(this.user.userPassword == "") {
          return "비밀번호를 입력해주세요.";
        }
        return "비밀번호을 확인해주세요.";
      },
      stateName() {
        return this.user.userName != null && regName.test(this.user.userName);
      },
      isValidName() {
        if(this.user.userName == "") {
          return "이름를 입력해주세요.";
        }
        return "이름을 확인해주세요.";
      },
      stateAddress() {
        return this.user.userAddress != null && regAddress.test(this.user.userAddress);
      },
      isValidAddress() {
        if(this.user.userAddress == "") {
          return "주소를 입력해주세요.";
        }
        return "주소를 확인해주세요.";
      },
      stateTel() {
        return this.user.userTel != null && regTel.test(this.user.userTel);
      },
      isValidTel() {
        if(this.user.userTel == "") {
          return "휴대폰 번호를 입력해주세요.";
        }
        return "휴대폰 번호를 확인해주세요.";
      },
    },
    data() {
      return {
        isDuplicated: false,
        user: {
          userId: "",
          userPassword: "",
          userName: "",
          userAddress: "",
          userTel: ""
        },
      }
    },
    methods: {
      async isDuplicatedID() {
        await idCheck( this.user.userId,
          ({ data }) => {
            this.isDuplicated = data;
          },
          (error) => {
            console.log(error);
          },
        );
        return this.isDuplicated;
      },
      onSubmit(event) {
        event.preventDefault();
        if (!(this.stateId && this.statePassword && this.stateName && this.stateAddress && this.stateTel)) {
          alert("입력한 정보들을 다시 한번 확인해주세요.")
          return;
        }
        this.user.userTel = this.user.userTel.replace(regNumber, "");
        registerUser(this.user,
          (response) => {
            console.log(response);
            alert("회원가입 되었습니다.");
            this.$router.push({ name: "Home" });
          },
          (error) => {
            console.log(error);
            alert("회원가입에 실패하였습니다.");
          },
        );
      }
    }
  }
</script>

<style scoped>
  .signup-container {
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