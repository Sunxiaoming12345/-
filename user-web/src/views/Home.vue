<template>
  <div class="home">
    <div class="categories">
      <el-tabs v-model="activeCategory" @tab-click="handleCategoryChange">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane v-for="category in categories" :key="category.id" :label="category.name" :name="category.id.toString()" />
      </el-tabs>
    </div>
    <div class="products">
      <div class="product-item" v-for="product in products" :key="product.id">
        <router-link :to="`/product/${product.id}`" class="product-link">
          <div class="product-image">
            <img :src="product.imageUrl || 'https://via.placeholder.com/200'" :alt="product.name" />
          </div>
          <div class="product-info">
            <h3 class="product-name">{{ product.name }}</h3>
            <p class="product-price">¥{{ product.price }}</p>
            <p class="product-stock" v-if="product.stock > 0">库存充足</p>
            <p class="product-stock out-of-stock" v-else>缺货</p>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getProducts, getCategories, getProductsByCategory, searchProducts } from '@/api/products'
import { ElMessage } from 'element-plus'

const route = useRoute()
const categories = ref([])
const products = ref([])
const activeCategory = ref('all')
const total = ref(0)
const searchKeyword = ref('')

const loadCategories = async () => {
  try {
    const res = await getCategories()
    categories.value = res
  } catch (error) {
    console.error('Failed to load categories:', error)
  }
}

const loadProducts = async () => {
  try {
    // 检查是否有搜索关键词
    if (searchKeyword.value) {
      // 调用搜索接口
      let res = await searchProducts(searchKeyword.value)
      // 如果选择了具体分类，再进行分类筛选
      if (activeCategory.value !== 'all') {
        const categoryId = parseInt(activeCategory.value)
        res = res.filter(product => product.categoryId === categoryId)
      }
      products.value = res
      total.value = res.length
    } else {
      // 对于推荐商品，直接获取所有推荐商品
      if (activeCategory.value === 'all') {
        const res = await getProducts()
        products.value = res
        total.value = res.length
      } else {
        // 对于分类商品，调用分类商品接口
        const categoryId = parseInt(activeCategory.value)
        const res = await getProductsByCategory(categoryId)
        products.value = res
        total.value = res.length
      }
    }
  } catch (error) {
    console.error('Failed to load products:', error)
  }
}

const handleCategoryChange = () => {
  // 切换分类时保留搜索关键词
  loadProducts()
}

// 监听路由变化，处理搜索关键词
watch(() => route.fullPath, (newPath) => {
  // 解析路由
  const url = new URL(newPath, window.location.origin)
  const keyword = url.searchParams.get('keyword')
  
  if (keyword) {
    searchKeyword.value = keyword
  } else {
    // 当没有搜索关键词时，清除搜索状态
    searchKeyword.value = ''
    activeCategory.value = 'all'
  }
  loadProducts()
}, { immediate: true })

onMounted(() => {
  loadCategories()
  loadProducts()
})
</script>

<style scoped>
.home {
  padding: 20px 0;
}

.categories {
  margin-bottom: 30px;
}

.products {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.product-item {
  border: 1px solid #eaeaea;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s;
}

.product-item:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.product-link {
  display: block;
  text-decoration: none;
  color: #333;
}

.product-image {
  height: 200px;
  overflow: hidden;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.product-item:hover .product-image img {
  transform: scale(1.05);
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 16px;
  margin-bottom: 10px;
  line-height: 1.4;
  height: 40px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.product-price {
  font-size: 18px;
  font-weight: bold;
  color: #ff4d4f;
  margin-bottom: 8px;
}

.product-stock {
  font-size: 12px;
  color: #666;
  margin-bottom: 12px;
}

.product-stock.out-of-stock {
  color: #ff4d4f;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}
</style>