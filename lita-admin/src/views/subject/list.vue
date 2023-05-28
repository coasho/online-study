<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>

    <el-tree
      ref="tree2"
      v-loading="loading"
      element-loading-text="数据加载中"
      :data="list"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from "@/api/edu/subject";
export default {
  name: "list",
  data() {
    return {
      filterText: '',
      list: [],
      loading:false,
      defaultProps: {
        children: 'subjectsTreeChild',
        label: 'title'
      }
    }
  },
  created() {
    this.loading=true;
    subject.getSubjectList().then(res=>{
      this.list=res.data.list;
      this.loading=false;
    })
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 30px 20px;
}
</style>
