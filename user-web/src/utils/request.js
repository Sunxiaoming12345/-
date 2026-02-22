import axios from 'axios'
import { useUserStore } from '@/store/user'

const request = axios.create({
    baseURL: '/',
    timeout: 10000
})

request.interceptors.request.use(
    (config) => {
        const user = useUserStore()
        if (user.token) {
            config.headers['Authorization'] = user.token
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    (response) => {
        const res = response.data
        if (res.code !== 1) {
            return Promise.reject(new Error(res.message || 'Error'))
        }
        return res
    },
    (error) => {
        console.error('err' + error)
        return Promise.reject(error)
    }
)

export default request