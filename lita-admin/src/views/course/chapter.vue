<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="输入关键字" style="padding-right: 10%">
        <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:1%;"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="dialogFormVisible = true">添加章节</el-button>
        <el-dialog title="编辑章节信息" :visible.sync="dialogFormVisible">
          <el-form>
            <el-form-item label="章节标题" :label-width="formLabelWidth">
              <el-input v-model="chapterInfo.title" autocomplete="off" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="章节序号" :label-width="formLabelWidth">
              <el-input-number :min="0" v-model="chapterInfo.sort" autocomplete="off" placeholder="请输入序号"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateChapter">确 定</el-button>
          </div>
        </el-dialog>

        <!-- 添加和修改课时表单 -->
        <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
          <el-form :model="video" label-width="120px">
            <el-form-item label="课时标题">
              <el-input v-model="video.title"/>
            </el-form-item>
            <el-form-item label="课时排序">
              <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
            </el-form-item>
            <el-form-item label="是否免费">
              <el-radio-group v-model="video.isFree">
                <el-radio :label="1">免费</el-radio>
                <el-radio :label="0">默认</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="上传视频">
              <el-upload
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/eduvod/video/upload'"
                :limit="1"
                class="upload-demo">
                <el-button size="small" type="primary" @click="saveVideoBtnDisabled=true">上传视频</el-button>
                <el-tooltip placement="right-end">
                  <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传
                  </div>
                  <i class="el-icon-question"/>
                </el-tooltip>
              </el-upload>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
            <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
          </div>
        </el-dialog>

        <el-tree
          ref="tree2"
          :data="chapterList"
          :props="defaultProps"
          :filter-node-method="filterNode"
          class="filter-tree"
          default-expand-all
          :expand-on-click-node="false">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>
              <i v-if="data.children==undefined" class="el-icon-edit-outline"/>
              {{ node.label }}
            </span>
            <span class="chapter-node" v-if="data.children!=undefined">
              <el-button type="text" size="mini"
                         @click="dialogVideoFormVisible = true; chapterId = data.id">添加小节</el-button>
              <el-button type="text" size="mini" @click="() => updateChapter(data)">编辑</el-button>
              <el-button type="text" size="mini" @click="() => removeChapter(data)">删除</el-button>
            </span>
            <span class="video-node" v-if="data.children==undefined">
              <el-button type="text" size="mini"
                         @click="dialogVideoFormVisible = true; chapterId = data.chapterId;videoFlag=1;getVideo(data.chapterId,data.id)">编辑</el-button>
              <el-button type="text" size="mini" @click="() => removeVideo(data.id)">删除</el-button>
            </span>
          </span>
        </el-tree>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import chapter from "@/api/edu/chapter";
import videoApi from "@/api/edu/video";
import vod from "@/api/edu/vod";

const defaultVideo = {// 课时对象
  title: '',
  sort: 0,
  isFree: 0,
  courseId: '',
  chapterId: '',
  videoSourceId: '',
  videoOriginalName: ''
}
const defaultChapter = {}
export default {
  name: 'chapter',
  data() {
    return {
      saveBtnDisabled: false,// 保存按钮是否禁用
      courseId: '',
      chapterId: '',
      saveVideoBtnDisabled: false, // 课时按钮是否禁用
      dialogVideoFormVisible: false, // 是否显示课时表单
      video: defaultVideo,
      chapterList: [],
      filterText: '',
      chapterInfo: defaultChapter,
      dialogTableVisible: false,
      dialogFormVisible: false,
      chapterFlag: 0,
      videoFlag: 0,
      defaultProps: {
        label: 'title'
      },
      formLabelWidth: '120px',
      fileList: [],//上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址
    }
  },
  created() {
    this.chapterInfo={};
    if (this.$route.params && this.$route.params.id) {
      let courseId = this.$route.params.id;
      this.courseId = courseId;
      this.chapterInfo.courseId = this.courseId;
      this.getChapterList(this.courseId);
    }
  },
  methods: {
    previous() {
      console.log('previous')
      this.$router.push({path: '/course/info/' + this.courseId})
    },
    next() {
      console.log('next')
      this.$router.push({path: '/course/publish/' + this.courseId})
    },
    init() {
      this.chapterFlag = 0;
      this.dialogFormVisible = false;
      this.dialogVideoFormVisible = false;
      this.videoFlag = 0;
      this.video = {};
      this.chapterInfo = {};
      this.chapterInfo.courseId = this.courseId;
      this.fileList = [];
      this.getChapterList(this.courseId);
    },
    getChapterList(courseId) {
      chapter.getChapterList(courseId).then(res => {
        this.chapterList = res.data.chapterList;
      }).catch(() => {
        this.$message({type: "error", message: "获取章节列表失败"});
      })
    },
    addOrUpdateChapter() {
      if (this.chapterFlag == 0) {
        this.dialogFormVisible = false;
        chapter.addChapter(this.chapterInfo).then(() => {
          this.$message({type: "success", message: "添加章节成功！"});
          this.init();
        }).catch(() => {
          this.$message({type: "error", message: "添加章节失败"});
          this.init();
        });
      } else {
        chapter.updateChapter(this.chapterInfo).then(() => {
          this.$message({type: "success", message: "修改章节成功！"});
          this.init();
        }).catch(() => {
          this.$message({type: "error", message: "修改章节失败"});
          this.init();
        });
      }
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    updateChapter(data) {
      this.chapterFlag = 1;
      this.getChapter(data.id);
      this.dialogFormVisible = true;
    },
    getChapter(chapterId) {
      this.chapterList.forEach((id, index, arr) => {
        if (chapterId == arr[index].id) {
          this.chapterInfo = arr[index];
        }
      })
    },
    getVideo(chapterId, videoId) {
      this.chapterList.forEach((id, index, arr) => {
        if (chapterId == arr[index].id) {
          this.chapterInfo = arr[index];
          this.chapterInfo.children.forEach((vId, vIndex, vArr) => {
            if (videoId == vArr[vIndex].id) {
              this.video = vArr[vIndex];
              if (this.video.videoOriginalName != '') {
                this.fileList = [{'name': this.video.videoOriginalName}];
              }
            }
          })
        }
      })
    },
    removeChapter(data) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return chapter.deleteChapterById(data.id)
      }).then(() => {
        this.$message({type: "success", message: "删除章节成功！"});
        this.init();
      }).catch(res => {
        if (res === 'cancel') {
          this.$message({type: "info", message: "已取消删除"});
        } else {
          this.$message({type: "error", message: "删除章节失败"});
        }
        this.init();
      })
    },
    saveOrUpdateVideo() {
      if (this.videoFlag == 0) {
        this.video.chapterId = this.chapterId;
        this.video.courseId = this.courseId;
        videoApi.addVideo(this.video).then(() => {
          this.$message({type: "success", message: "添加小节成功！"});
          this.dialogVideoFormVisible = false;
        }).catch(() => {
          this.$message({type: "error", message: "添加小节失败"});
          this.dialogVideoFormVisible = false;
        });
      } else {
        this.video.chapterId = this.chapterId;
        this.video.courseId = this.courseId;
        videoApi.updateVideo(this.video).then(() => {
          this.$message({type: "success", message: "编辑小节成功！"});
          this.dialogVideoFormVisible = false;
        }).catch(() => {
          this.$message({type: "error", message: "编辑小节失败"});
          this.dialogVideoFormVisible = false;
        });
      }
    },
    removeVideo(id) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return videoApi.deleteVideoById(id)
      }).then(() => {
        this.$message({type: "success", message: "删除课时成功！"});
        this.init();
      }).catch(res => {
        if (res === 'cancel') {
          this.$message({type: "info", message: "已取消删除"});
        } else {
          this.$message({type: "error", message: "删除课时失败"});
        }
        this.init();
      })
    },
    handleVodUploadSuccess(response) {
      console.log("VodUploadSuccess");
      this.saveVideoBtnDisabled=false;
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName = response.data.title;
    },
    handleVodRemove(file, fileList) {
      console.log("handleVodRemove");
      vod.removeById(this.video.videoSourceId).then(res => {
        this.video.videoSourceId = '';
        this.video.videoOriginalName = '';
        this.fileList = [];
        this.$message({
          type: 'success',
          message: res.message
        })
      })
    },
    beforeVodRemove(file, fileList) {
      console.log("beforeVodRemove");
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    handleUploadExceed() {
      console.log("handleUploadExceed");
      this.$message.warning('想要重新上传视频，请先删除已上传的视频');
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    },
    dialogFormVisible(val) {
      if (val == false) {
        this.init();
      }
    },
    dialogVideoFormVisible(val) {
      if (val == false) {
        this.init();
      }
    }
  }
}
</script>

<style scoped>
.filter-tree {
  margin: 1% 0 3% 0;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

/deep/ .video-node {
  position: absolute;
  right: calc(15% + 5px);
}

/deep/ .chapter-node {
  position: absolute;
  right: 15%;
}
</style>
