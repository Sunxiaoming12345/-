import request from '@/utils/request'

export function getCartList() {
    return request({
        url: '/user/shopCart/list',
        method: 'get'
    })
}

export function addToCart(data) {
    return request({
        url: '/user/shopCart/add',
        method: 'post',
        data
    })
}

export function updateCartItem(data) {
    return request({
        url: '/user/shopCart/updateQuantity',
        method: 'put',
        params: {
            productId: data.productId,
            quantity: data.quantity
        }
    })
}

export function removeCartItem(id) {
    return request({
        url: '/user/shopCart/delete',
        method: 'delete',
        params: {
            productId: id
        }
    })
}