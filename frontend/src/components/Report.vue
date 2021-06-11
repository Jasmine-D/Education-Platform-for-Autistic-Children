<template>
    <q-dialog v-model="popUp" full-height persistent>
        <q-card style="width:65vw;max-width:80vw;overflow:hidden;">
            <div style="width:100%;height:100%;" ref="report">
                <div style="width:100%;height:100%;float:left">
                    <img src="../assets/bg4.png" style="width:100%;height:100%;"/>
                </div>
                <div style="float:left;color:#000000;margin-top:-52%;margin-left:16%;width:40vw;padding:0% 5%;font-family:'楷体'">
                    <div style="text-align:center;font-size:25px">
                        <span>情景主题:{{sceneName}}</span>
                    </div>
                    <div style="font-size:19px;margin-top:3%">学习参与者：{{report.teacher}}老师,{{user.username}}</div>
                    <div style="font-size:19px;margin-top:3%;overflow:hidden;white-space: nowrap;text-overflow:ellipsis">
                        学习内容：{{words}}
                    </div>
                    <div style="font-size:19px;margin-top:3%">学习日期：{{report.time}}</div>
                    <div style="font-size:19px;margin-top:3%">老师评分：
                        <q-rating
                            v-model="report.score"
                            size="2.5em"
                            color="grey"
                            :color-selected="ratingColors"
                            :max="5"
                            icon="local_florist"
                            readonly
                        />
                    </div>
                    <div style="font-size:19px;margin-top:3%;">
                        <div style="float:left">老师评语：</div>
                        <div style="float:left;width:73%;margin-left:1%;">{{report.comment}}</div>
                        <div style="clear:both"></div>
                    </div>
                    
                </div>
                <div style="float:left;margin-top:-6%;margin-left:75%;width:300px;font-family:'楷体';" ref="button">
                    <div style="float:left;font-size:20px;margin-right:5%;">
                        <q-btn rounded size="md" color="primary" @click="saveImg()">保存图片</q-btn>
                    </div>
                    <div style="float:left;font-size:20px;">
                        <q-btn rounded size="md" color="primary" @click="closeReport()">关闭</q-btn>
                    </div>
                    <div style="clear"></div>
                </div>
                <div style="clear:both"></div>
            </div>
            
        </q-card>
    </q-dialog>
</template>

<script>
import html2canvas from 'html2canvas'
export default {
    name:'Report',
    props:{
        popUp:{
            type:Boolean,
            default:false
        },
        report:{
            type:Object
        },
        sceneName:{
            type:String
        },
        words:{
            type:String
        },
        close:{
            type:Function
        }
    },
    data(){
        
        return{
            user:'',
            ratingColors:['pink-3','pink-5','orange-8','deep-orange-13','red-14'],
        }
    },
    methods:{
        closeReport(){
            this.close(false);
        },
        saveImg(){
            let canvasID = this.$refs.report;
            let removeDiv = this.$refs.button;
            canvasID.removeChild(removeDiv)
            html2canvas(canvasID, {
                backgroundColor: null     // 解决生成的图片有白边
            }).then(canvas => {
                canvasID.appendChild(removeDiv)
                let dataURL = canvas.toDataURL('image/png');
                let filename="report_"+this.convertTime(new Date());
                this.saveFile(dataURL, filename)
            })
        },
        saveFile (data, filename) {
            let saveLink = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
            saveLink.href = data;
            saveLink.download = filename;
            let event = document.createEvent('MouseEvents')
            event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
            saveLink.dispatchEvent(event);
        },
        convertTime(t){
            var time=new Date(t);
            var year=time.getFullYear();
            var month=time.getMonth()+1;
            var day=time.getDate();
            return year+'-'+(month>=10?month:('0'+month))+'-'+(day>=10?day:('0'+day));
        },

    },
    created(){
        this.user=JSON.parse(sessionStorage.getItem('user'));
    }
}
</script>