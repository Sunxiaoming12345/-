import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        token: localStorage.getItem('userToken') || null,
        userInfo: JSON.parse(localStorage.getItem('userInfo')) || null
    }),
    getters: {
        isLoggedIn: (state) => !!state.token
    },
    actions: {
        login(token, userInfo) {
            this.token = token
            this.userInfo = userInfo
            localStorage.setItem('userToken', token)
            localStorage.setItem('userInfo', JSON.stringify(userInfo))
        },
        logout() {
            this.token = null
            this.userInfo = null
            localStorage.removeItem('userToken')
            localStorage.removeItem('userInfo')
        }
    }
})