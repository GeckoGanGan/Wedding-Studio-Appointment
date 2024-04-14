<template>
    <!-- 用户表单页面 -->
    <div>
        <div class="filter">
            <el-input style="width: 50%;" v-model="pageQuery.queryConditions.username" placeholder="请输入用户名"></el-input>
            <el-button type="primary" style="margin-left: 20px;" @click="list">搜索</el-button>
        </div>
        <el-table :data="tableData" style="width: 100%">

            <el-table-column label="用户名" >
                <template v-slot="scope">
                    {{ scope.row.username }}
                </template>
            </el-table-column>
            <!-- 用户昵称 -->
            <el-table-column label="昵称" >
                <template v-slot="scope">
                    {{ scope.row.nickName }}
                </template>
            </el-table-column>
            <el-table-column label="邮箱" >
                <template v-slot="scope">
                    {{ scope.row.userEmail }}
                </template>
            </el-table-column>
            <el-table-column label="手机号" >
                <template v-slot="scope">
                    {{ scope.row.phoneNumber }}
                </template>
            </el-table-column>
            <!-- 用户头像 -->
            <el-table-column label="头像" >
                <template v-slot="scope">
                    <img :src="scope.row.avatar" style="width: 100px; height: 100px;">
                </template>
            </el-table-column>

            <!-- 用户性别： 0-男，1-女  -->
            <el-table-column label="性别" >
                <template v-slot="scope">
                    {{ scope.row.sex == 0 ? '男' : '女' }}
                </template>
            </el-table-column>

            <!-- 用户状态： 0-正常，1-禁用  -->
            <el-table-column label="状态" >
                <template v-slot="scope">
                    {{ scope.row.status == 0 ? '正常' : '禁用' }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template v-slot="scope">
                    <img class="icon" :src="require('@/assets/icons/编辑.svg')" @click="handleEdit(scope.row.id)">
                    <img class="icon" :src="require('@/assets/icons/删除.svg')" @click="handleDelete(scope.row.id)">
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination v-model:current-page="pageQuery.pageNum" v-model:page-size="pageQuery.pageSize"
            :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div>


</template>

<script>
import { getAllUsers } from "@/api/user";
export default {
    name: 'List',
    data() {
        return {
            total: Number,
            tableData: [],
            pageQuery: {
                pageNum: 0,
                pageSize: 5,
                queryConditions: {
                    username: '',
                }
            }
        }
    },
    created() {
        this.list();
    },
    methods: {
        list() {
            getAllUsers(this.pageQuery).then(res => {
                this.tableData = res.records;
                this.total = res.total;
                console.log('-----------------------------');
                console.log(res);
            }).catch(err => {
                console.log(err);
            });
        },
        handleSizeChange(size) {
            this.pageQuery.pageSize = size;
            this.list();
        },
        handleCurrentChange(currentPage) {
            this.pageQuery.pageNum = currentPage;
            this.list();
        }
    }
}
</script>

<style scoped>
.filter {
    display: flex;
    align-items: center; /* 垂直居中 */
    margin-bottom: 10px;
}
.icon{
    width: 30px;
    height: 30px;
    margin-right: 10px;
}
.el-pagination {
    text-align: right;
}
</style>