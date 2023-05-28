<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title"
                  placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>
      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-cascader
          v-model="subjectId"
          :options="subjectTree"
          :props="{
            value:'id',
            children: 'subjectsTreeChild',
            label: 'title',
            expandTrigger: 'hover'
          }"
          @change="handleChange"/>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right"
                         placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"></tinymce>
      </el-form-item>

      <!-- 课程封面 TODO -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
          :action="BASE_API+'/eduoss/fileoss/upload?host=cover'"
          class="avatar-uploader">
          <img class="cover-image" :src="courseInfo.cover" width="50%">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right"
                         placeholder="免费课程请设置为0元"/>
      </el-form-item>
    </el-form>

    <el-form label-width="120px">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import course from "@/api/edu/course";
import teacher from "@/api/edu/teacher";
import subject from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";

const defaultForm = {
  title: '',
  subjectId: '',
  subjectParentId: '',
  teacherId: '',
  lessonNum: 0,
  description: '',
  cover: process.env.OSS_PATH + '/cover/defaultCover.jpg',
  price: 0
}
export default {
  name: 'info',
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseInfo: defaultForm,
      courseId: '',
      teacherList: [],
      subjectTree: [],
      subjectId: [],
      BASE_API: process.env.BASE_API,
      isSaved: false
    }
  },
  created() {
    this.courseInfo = {};
    this.courseInfo.cover = process.env.OSS_PATH + '/cover/defaultCover.jpg';
    this.subjectId = [];
    console.log(this.courseInfo);
    this.getTeacherList();
    subject.getSubjectList().then(res => {
      this.subjectTree = res.data.list;
    });
    this.getCourseInfo();
  },
  methods: {
    getCourseInfo() {
      if (this.$route.params && this.$route.params.id) {
        course.getCourseInfo(this.$route.params.id).then(res => {
          if (res.data.courseInfo != null) {
            this.isSaved = true;
            this.courseId = this.$route.params.id;
            this.courseInfo = res.data.courseInfo;
            this.subjectId = [this.courseInfo.subjectParentId, this.courseInfo.subjectId];
          }
        }).catch(() => {
          this.$message({type: "error", message: "获取课程信息失败"});
        })
      }
    },
    next() {
      if (this.isSaved) {
        course.updateCourseInfo(this.courseInfo).then(res => {
          this.$message({type: "success", message: "保存课程基本信息成功！"});
          this.$router.push({path: '/course/chapter/' + this.courseId});
        }).catch(() => {
          this.$message({type: "error", message: "请输入有效数据"});
        });
      } else {
        course.saveCourseInfo(this.courseInfo).then(res => {
          this.$message({type: "success", message: "保存课程基本信息成功！"});
          this.$router.push({path: '/course/chapter/' + res.data.id});
        }).catch(() => {
          this.$message({type: "error", message: "请输入有效数据"});
        });
      }

    },
    getTeacherList() {
      teacher.getAll().then(res => {
        this.teacherList = res.data.items;
      }).catch(() => {
        this.$message({type: "error", message: "获取讲师列表失败"});
      })
    },
    handleChange(list) {
      this.courseInfo.subjectParentId = list[list.length - 2];
      this.courseInfo.subjectId = list[list.length - 1];
    },
    handleCoverSuccess(res, file) {
      console.log(res)// 上传响应
      console.log(URL.createObjectURL(file.raw))// base64编码
      this.courseInfo.cover = res.data.url;
    },
    beforeCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  },
  watch: {
    $route(to, from) {
      this.courseInfo = defaultForm;
      this.subjectId = [];
    }
  }
  ,
  components: {
    Tinymce
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px 80px 20px 20px;
}

.cover-image {
  margin-left: -50%;
}
</style>
