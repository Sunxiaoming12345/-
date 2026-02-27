import request from '@/utils/request'

export function getProducts(params) {
  return request({
    url: '/user/products/recommended',
    method: 'get'
  })
}

export function getProductDetail(id) {
  return request({
    url: `/user/products/detail/${id}`,
    method: 'get'
  })
}

export function getCategories() {
  return request({
    url: '/user/products/categories',
    method: 'get'
  })
}

export function getProductsByCategory(categoryId) {
  return request({
    url: `/user/products/category/${categoryId}`,
    method: 'get'
  })
}