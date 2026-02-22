<template>
  <div class="profile">
    <h2>个人中心</h2>
    <div class="profile-content">
      <div class="profile-info">
        <div class="profile-avatar">
          <el-avatar :size="80">{{ userStore.userInfo?.username?.charAt(0) || 'U' }}</el-avatar>
        </div>
        <div class="profile-details">
          <h3>{{ userStore.userInfo?.username || '用户' }}</h3>
          <p>邮箱: {{ userStore.userInfo?.email || '未设置' }}</p>
          <p>注册时间: {{ userStore.userInfo?.createdTime || '未知' }}</p>
        </div>
      </div>
      <div class="profile-actions">
        <el-button type="primary" @click="editProfile">编辑资料</el-button>
        <el-button type="danger" @click="userStore.logout(); router.push('/login')">退出登录</el-button>
      </div>
    </div>
    <div class="profile-stats">
      <h3>我的统计</h3>
      <div class="stats-grid">
        <div class="stat-item">
          <el-icon><Goods /></el-icon>
          <div class="stat-content">
            <div class="stat-number">0</div>
            <div class="stat-label">收藏商品</div>
          </div>
        </div>
        <div class="stat-item">
          <el-icon><Tickets /></el-icon>
          <div class="stat-content">
            <div class="stat-number">0</div>
            <div class="stat-label">全部订单</div>
          </div>
        </div>
        <div class="stat-item">
          <el-icon><Money /></el-icon>
          <div class="stat-content">
            <div class="stat-number">¥0</div>
            <div class="stat-label">总消费</div>
          </div>
        </div>
        <div class="stat-item">
          <el-icon><Star /></el-icon>
          <div class="stat-content">
            <div class="stat-number">0</div>
            <div class="stat-label">评价数</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { Goods, Tickets, Money, Star } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const editProfile = () => {
  ElMessage.info('编辑资料功能待实现')
}
</script>

<style scoped>
.profile {
  padding: 20px 0;
}

.profile h2 {
  margin-bottom: 30px;
  color: #333;
}

.profile-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 8px;
  margin-bottom: 40px;
}

.profile-info {
  display: flex;
  align-items: center;
  gap: 30px;
}

.profile-avatar {
  margin-right: 20px;
}

.profile-details h3 {
  margin: 0 0 10px 0;
  color: #333;
}

.profile-details p {
  margin: 5px 0;
  color: #666;
  font-size: 14px;
}

.profile-actions {
  display: flex;
  gap: 15px;
}

.profile-stats {
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 8px;
}

.profile-stats h3 {
  margin: 0 0 30px 0;
  color: #333;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 15px;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.stat-item el-icon {
  font-size: 24px;
  color: #409EFF;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}
</style>