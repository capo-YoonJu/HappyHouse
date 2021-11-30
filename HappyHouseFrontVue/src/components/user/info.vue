<template>
  <div>
    <div class="info-container">
      <h2>마이페이지</h2>
      <b-form class="info-form" @submit="onSubmit">
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
            required
            :state="stateTel"
          ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary">저장</b-button>
        <b-button variant="danger" v-b-modal.signout>회원 탈퇴</b-button>

        <b-modal id="signout" title="회원 탈퇴">
          <b-form-group label="비밀번호 확인" label-for="password-check">
            <b-form-input
              id="password-check-input"
              v-model="passwordCheck"
              type="password"
              required
            ></b-form-input>
          </b-form-group>
          <template #modal-footer>
            <b-button size="sm" variant="danger" @click="signout()">
              회원 탈퇴
            </b-button>
          </template>
        </b-modal>
      </b-form>
    </div>
  </div>
</template>

<script>
  import { login, updateUser } from "@/api/user.js";
  import { mapActions } from 'vuex';
  const regPassword = /^[A-Za-z0-9]{4,16}$/;
  const regName = /^[가-힣A-Za-z]{2,20}$/;
  const regAddress = /^[가-힣0-9]{2,30}$/;
  const regTel = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
  export default {
    data() {
      return {
        passwordCheck: null,
        user: {
          userId: "",
          userPassword: "",
          userName: "",
          userAddress: "",
          userTel: ""
        },
      }
    },
    computed: {
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
    created() {
      this.user = this.$store.state.userStore.userInfo;
    },
    methods: {
      ...mapActions("userStore", ["deleteUserInfo"]),
      onSubmit(event) {
        event.preventDefault();
        if (!(this.statePassword && this.stateName && this.stateAddress && this.stateTel)) {
          alert("입력한 정보들을 다시 한번 확인해주세요.")
          return;
        }
        updateUser(this.user, 
          (response) => {
            console.log(response);
            this.user.userPassword = null;
            this.$store.commit("userStore/SET_USER_INFO", this.user);
            alert("회원 정보가 수정되었습니다.");
            this.$router.push({ name: "Home" });
          },
          (error) => {
            console.log(error);
            alert("회원 정보를 수정하는데 실패하였습니다.");
          }
        )
      },
      signout() {
        login( {
            userId: this.user.userId,
            userPassword: this.passwordCheck
          },
          ({ data }) => {
            if (data.message === "success") {
              this.deleteUserInfo()
              .then( () => {
                  alert("회원 탈퇴되었습니다.");
                  this.$router.push({ name: "Home" });
                }
              )
              .catch( (error) => {
                  console.log(error);
                  alert("회원 탈퇴에 실패하였습니다.");
                }
              );
            }else {
              alert("회원 탈퇴에 실패하였습니다. 비밀번호를 확인해주세요.");
            }
          },
          (error) => {
            console.log(error);
            alert("회원 탈퇴에 실패하였습니다.");
          },
        );
        this.passwordCheck = null;
      },
    },
  }
</script>

<style scoped>
  .info-container {
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
    margin: 0.5rem 1.5rem;
    width: 30%;
  }
</style>