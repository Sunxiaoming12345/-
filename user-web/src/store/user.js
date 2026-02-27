import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('user_token') || '')
  const username = ref(localStorage.getItem('user_username') || '')

  function setToken(t) {
    token.value = t
    localStorage.setItem('user_token', t)
  }

  function setUsername(name) {
    username.value = name
    localStorage.setItem('user_username', name)
  }

  function logout() {
    token.value = ''
    username.value = ''
    localStorage.removeItem('user_token')
    localStorage.removeItem('user_username')
  }

  return { token, username, setToken, setUsername, logout }
})