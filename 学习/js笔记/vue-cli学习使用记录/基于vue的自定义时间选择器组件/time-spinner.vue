<template>
    <div>
        <div>
            <div class="time-list time-list-hour">
                <div class="time-list-title">小时</div>
                <div class="time-hour" ref="hours">
                    <ul class="time-list-ui" ref="hoursList">
                        <li :class="item.selected?'selected-time':''" v-for="item in hoursList"
                            @click="handleClick('hours', item)">{{item.text}}
                        </li>
                    </ul>
                </div>
            </div>
            <div class="time-list time-list-minute">
                <div class="time-list-title">分钟</div>
                <div class="time-minute" ref="minutes">
                    <ul class="time-list-ui" ref="minutesList">
                        <li :class="item.selected?'selected-time':''" v-for="item in minutesList"
                            @click="handleClick('minutes', item)">
                            {{item.text}}
                        </li>
                    </ul>
                </div>
            </div>
            <div class="time-list time-list-second">
                <div class="time-list-title">秒数</div>
                <div class="time-second" ref="seconds">
                    <ul class="time-list-ui" ref="secondsList">
                        <li :class="item.selected?'selected-time':''" v-for="item in secondsList"
                            @click="handleClick('seconds', item)">
                            {{item.text}}
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="time-confirm" @keydown.tab.capture="">
            <ui-row>
                <ui-col span="15" offset="1">
                    <ui-row>
                        <ui-col span="6">
                            <Input
                                    ref="hourInput"
                                    class="hourInput"
                                    v-model="hourValue"
                                    size="small"
                                    :maxlength="2"
                                    @on-focus = "handleFocus('hour',$event)"
                                    @on-blur = "handleBlur"
                                    @keydown.native="handleKeydown"
                                    @on-change="handleChange('hour',$event)"></Input>
                        </ui-col>
                        <ui-col span="1" offset="1"><strong>:</strong></ui-col>
                        <ui-col span="6">
                            <Input ref="minuteInput"
                                   v-model="minuteValue"
                                   size="small"
                                   :maxlength="2"
                                   @on-focus = "handleFocus('minute',$event)"
                                   @on-blur = "handleBlur"
                                   @keydown.native="handleKeydown"
                                   @on-change="handleChange('minute',$event)"></Input>
                        </ui-col>
                        <ui-col span="1" offset="1"><strong>:</strong></ui-col>
                        <ui-col span="6">
                            <Input ref="secondInput"
                                   v-model="secondValue"
                                   size="small"
                                   :maxlength="2"
                                   @on-focus = "handleFocus('second',$event)"
                                   @on-blur = "handleBlur"
                                   @keydown.native="handleKeydown"
                                   @on-change="handleChange('second',$event)"></Input>
                        </ui-col>
                    </ui-row>
                </ui-col>
                <ui-col span="6" offset="1">
                    <i-button size="small" long @click.native="clear">
                        清空
                    </i-button>
                    <!--<i-button size="small" type="primary" @click.native="submit">-->
                        <!--确定-->
                    <!--</i-button>-->
                </ui-col>
            </ui-row>
        </div>
    </div>
</template>
<script type="text/ecmascript-6">
    import {scrollTop} from './scrollAnimation';
    export default {
        name: 'TimeSpinner',
        props: {
            value: {
                type: String,
                default: ''
            },
        },
        data() {
            return {
                hourValue: '',
                minuteValue: '',
                secondValue: '',
                focus:'',           // 焦点位置
            };
        },
        computed: {
            // 生成时间选项列表
            hoursList() {
                return this.makeTimeList(24);
            },
            minutesList() {
                return this.makeTimeList(60);
            },
            secondsList() {
                return this.makeTimeList(60);
            }
        },
        methods: {
            makeTimeList(limit) {
                let arrTime = [];
                for (let i = 0; i < limit; i++) {
                    let time = {
                        text: this.formatTime(i),
                        selected: false,
                    };
                    arrTime.push(time);
                }
                return arrTime;
            },
            clear() {
                this.hourValue = '';
                this.minuteValue = '';
                this.secondValue='';
                this.$emit('update:value', '');
                this.$emit('on-clear');
            },
            submit() {
                this.$emit('on-submit');
            },
            handleFocus(val,event){
                this.focus = val;
                event.currentTarget.select();
            },
            handleBlur(){
                this.focus = '';
            },
            initTime(){
                if (this.value.indexOf(':') >= 0) {
                    var arr = this.value.split(':');
                    this.hourValue = arr[0] || '';
                    this.minuteValue = arr[1] || '';
                    this.secondValue = arr[2] || '';
                } else {
                    return '';
                }
            },
            handleKeydown(e){        // 当只输入一位数时按下tab和enter会自动补0并将焦点移至下一输入框
                const keyCode = e.keyCode;
                if (keyCode === 13 || keyCode === 9){
                    switch (this.focus) {
                        case 'hour':
                            this.hourValue = this.formatTime(this.hourValue);
                            this.$refs['minuteInput'].focus();
                            break;
                        case 'minute':
                            this.minuteValue = this.formatTime(this.minuteValue);
                            this.$refs['secondInput'].focus();
                            break;
                        case 'second':
                            this.secondValue = this.formatTime(this.secondValue);
                            if (keyCode === 13){this.submit();}
                            break;
                    }
                    this.$emit('update:value', this.getValue());
                }
            },
            handleClick(type, cell) {
                switch (type) {
                    case 'hours': this.hourValue = cell.text; this.$emit('update:value', this.getValue());break;
                    case 'minutes': this.minuteValue = cell.text; this.$emit('update:value', this.getValue());break;
                    case 'seconds': this.secondValue = cell.text; this.$emit('update:value', this.getValue());break;
                }
                cell.selected = true;
                this.$emit('on-change');
            },
            formatTime (text) {
                return text.toString().length < 2 ? '0' + text : ''+text;
            },
            // 更新数据
            getValue () {
                if (isNaN(this.hourValue)) this.hourValue= '';
                if (isNaN(this.minuteValue)) this.minuteValue= '';
                if (isNaN(this.secondValue)) this.secondValue= '';
                return this.hourValue+':'+this.minuteValue+':'+this.secondValue;
            },
            handleChange (type,event) {
                let val = event.target.value.trim();
                switch (type) {
                    case 'hour' :
                        this.hourValue = this.timeAutoChange(this.hourValue,24);
                        if (val.length>1){this.$refs['minuteInput'].focus();}
                        break;
                    case 'minute' :
                        this.minuteValue = this.timeAutoChange(this.minuteValue,60);
                        if (val.length>1){this.$refs['secondInput'].focus();}
                        break;
                    case 'second' :
                        this.secondValue = this.timeAutoChange(this.secondValue,60);
                        break;
                }
                this.$emit('update:value', this.getValue());
                this.$emit('on-change');
            },
            timeAutoChange(time,limit) {
                return time >= limit?this.formatTime(parseInt(time%limit)):time;
            },
            updateScroll (time, type){
                if (isNaN(time)) return;
                let index = Number(time);
                const from = this.$refs[type].scrollTop;
                const to = 24 * index;
                scrollTop(this.$refs[type], from, to, 500);
            }
        },
        watch: {
            hourValue (newVal, oldVal) {
                this.hoursList.forEach((item) => {
                    if(item.text === newVal) item.selected = true;
                    if(item.text === oldVal) item.selected = false;
                });
                this.$nextTick(() =>{
                    this.updateScroll(newVal, 'hours');
                });
            },
            minuteValue (newVal, oldVal) {
                this.minutesList.forEach((item) => {
                    if (item.text === newVal) item.selected = true;
                    if (item.text === oldVal) item.selected = false;
                });
                this.$nextTick(() =>{
                    this.updateScroll(newVal, 'minutes');
                });
            },
            secondValue (newVal, oldVal) {
                this.secondsList.forEach((item) => {
                    if (item.text === newVal) item.selected = true;
                    if (item.text === oldVal) item.selected = false;
                });
                this.$nextTick(() =>{
                    this.updateScroll(newVal, 'seconds');
                });
            },
            value (val){
                if (val.indexOf(':') >= 0) {
                    var arr = val.split(':');
                    this.hourValue = arr[0] || '';
                    this.minuteValue = arr[1] || '';
                    this.secondValue = arr[2] || '';
                }else {
                    if (val !== ''){
                        this.hourValue = this.formatTime(parseInt(val/10000));
                        this.minuteValue =  this.formatTime(parseInt(val/100%100));
                        this.secondValue = this.formatTime(parseInt(val%100));
                    }
                    else {
                        this.hourValue = '';
                        this.minuteValue = '';
                        this.secondValue = '';
                    }
                }
            }
        },
    };
</script>
<style lang="less" scoped>
    .time-list {
        margin-top: 4px;
        margin-bottom: 4px;
        width: 30%;
        float: left;
        position: relative;
    }

    .time-list-hour {
        margin-left: 2%;
    }

    .time-list-minute {
        margin-left: 3%;
        margin-right: 3%;
    }

    .time-list-title {
        text-align: center;
        margin-bottom: 5px;
    }

    .time-hour, .time-minute, .time-second {
        height: 144px;
        border: 1px solid #e8eaec;
        overflow: hidden;
    }

    .time-hour:hover, .time-minute:hover, .time-second:hover {
        overflow-y: auto;
    }

    .time-list ul {
        width: 100%;
        margin: 0;
        padding: 0 0 120px 0;
        list-style: none
    }

    .time-list ul li {
        width: 100%;
        height: 24px;
        line-height: 24px;
        margin: 0;
        padding: 0 0 0 16px;
        -webkit-box-sizing: content-box;
        box-sizing: content-box;
        text-align: left;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        cursor: pointer;
        list-style: none;
        -webkit-transition: background .2s ease-in-out;
        transition: background .2s ease-in-out
    }
    .time-list ul li:hover {
        background-color: #F3F3F3;
        color: #33AFFA;
    }
    .selected-time {
        background-color: #F3F3F3;
        color: #33AFFA;
    }
    .time-confirm{
        padding: 4px 0 4px 0;
        height: 38px;
        border-top:1px solid #e8eaec;
        clear:both
    }
</style>