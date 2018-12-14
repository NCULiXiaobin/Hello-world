<!--
* ***************************************************************************
* Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
*
* 文 件 名：timePicker
* 模块名称：时间选择器
* 功能说明：时间选择器   可编辑属性：size,disabled,readonly,placeholder
* 作    者：lixiaobin@szkingdom.com
* 创建日期：20181008
* 版 本 号：1.0
* 修改历史：
* 修改日期                      姓名                    内容
* ---------------------------------------------------------------------------
* ****************************************************************************
-->
<template>
    <div v-click-outside.capture="handleClose">
        <div>
            <Input ref="input"
                   :value="visualValue"
                   :readonly="readonly"
                   :disabled="disabled"
                   :clearable="clearable"
                   :size="size"
                   :placeholder="placeholder"
                   icon="ios-clock-outline"
                   :maxlength="8"
                   @keydown.native="handleKeydown"
                   @click.native="handleFocus"
                   @on-focus="handleFocus"
                   @on-blur="handleBlur"></Input>
        </div>
        <transition name="transition-drop">
            <div class="time-drop" v-show="timeDrop">
                <div class="time-body">
                    <div class="time-head">时间选择</div>
                    <div class="time-content">
                        <time-spinner
                                ref="timeSpinner"
                                @on-change="timeChange"
                                :value.sync="timeValue"
                                @on-submit="submit"
                                @on-clear="handleClear">
                        </time-spinner>
                    </div>
                </div>
            </div>
        </transition>
    </div>
</template>

<script type="text/ecmascript-6">
    import TimeSpinner from './time-spinner.vue';
    import {directive as clickOutside} from 'v-click-outside-x';
    import Emitter from './emitter';

    export default {
        name: 'TimePicker',
        mixins: [ Emitter ],
        components: {TimeSpinner},
        directives: {clickOutside},
        props: {
            value: {
                type: String,
                default: ''
            },
            readonly: {
                type: Boolean,
                default: true
            },
            disabled: {
                type: Boolean,
                default: false
            },
            editable: {
                type: Boolean,
                default: true
            },
            clearable: {
                type: Boolean,
                default: false
            },
            placeholder: {
                type: String,
                default: '请选择时间'
            },
            size: {
                validator(value) {
                    for (let i = 0; i < ['small', 'large', 'default'].length; i++) {
                        if (value === ['small', 'large', 'default'][i]) {
                            return true;
                        }
                    }
                    return false;
                },
                default() {
                    return !this.$IVIEW || this.$IVIEW.size === '' ? 'default' : this.$IVIEW.size;
                }
            },
        },
        data() {
            return {
                timeValue: this.visualValue,
                isFocused: false,
                timeDrop: false,
            };
        },
        computed: {
            visualValue () {
                return this.initFormatTime(this.value);
            },
        },
        methods: {
            timeChange() {
                this.$emit('input', this.timeValue);     // update v-model
                this.emitChange();
            },
            emitChange () {
                this.$nextTick(() => {
                    this.dispatch('FormItem', 'on-form-change', this.timeValue);
                });
            },
            handleClear() {
                this.timeValue = '';
                this.$emit('on-clear');
                this.dispatch('FormItem', 'on-form-change', '');
                this.emitChange();
            },
            handleClose() {
                if (this.timeValue) {
                    let arr = this.timeValue.split(':');
                    arr[0] = arr[0] ? arr[0] : '00';
                    arr[1] = arr[1] ? arr[1] : '00';
                    arr[2] = arr[2] ? arr[2] : '00';
                    this.timeValue = arr[0] + ':' + arr[1] + ':' + arr[2];
                }
                this.$nextTick(() => {
                    this.$emit('on-change',this.timeValue);
                });
                this.timeDrop = false;
            },
            submit() {
                this.timeDrop = false;
            },
            handleFocus(e) {
//                if (this.readonly) return;
                this.isFocused = true;
                if (e && e.type === 'focus') return;
                if (!this.disabled) {
                    this.timeValue = this.visualValue;
                    this.$nextTick(() => {
                        this.$refs['timeSpinner'].$refs['hourInput'].focus();
                    });
                    this.timeDrop = true;
                }
            },
            handleBlur() {
                this.isFocused = false;
                if (this.timeValue) this.timeValue = this.initFormatTime(this.timeValue);
                this.$emit('on-blur');
            },
            handleKeydown(e) {
                const keyCode = e.keyCode;

                // close on "esc" key
                if (keyCode === 27) {
                    if (this.timeDrop) {
                        e.stopPropagation();
                        this.handleClose();
                    }
                }
                //  "enter" key
//                if (keyCode === 13) {
//                    e.stopPropagation();
//                    this.timeValue = this.initFormatTime(this.timeValue);
//                }
            },
            formatTime(text) {
                return text < 10 ? '0' + text : text;
            },
            initFormatTime(text) {
                if (text.indexOf(':') < 0) {
                    if (text !== '') {
                        return this.formatTime(parseInt(text / 10000 % 24)) + ':'
                            + this.formatTime(parseInt(text / 100 % 100 % 60)) + ':'
                            + this.formatTime(parseInt(text % 100 % 60));
                    } else {
                        return '';
                    }
                } else {
                    let arr = text.split(':');
                    let t_hour = arr[0];
                    let t_minute = arr[1];
                    let t_second = arr[2];
                    if (t_hour >= 24) {
                        t_hour = parseInt(t_hour % 24);
                    }
                    if (t_minute >= 60) {
                        t_minute = parseInt(t_minute % 60);
                    }
                    if (t_second >= 60) {
                        t_second = parseInt(t_second % 60);
                    }
                    return t_hour + ':' + t_minute + ':' + t_second;
                }
            },
        },
        watch: {
            timeValue(newVal){
                this.$emit('input', newVal);
            }
        },
    };
</script>
<style lang="less" scoped>
    .time-drop {
        min-width: 200px;
        border: 1px solid #e8eaec;
        background-color:#fff;
        position:absolute;
        z-index:1080;
        -webkit-box-sizing:border-box;
        box-sizing:border-box;
        border-radius:4px;
        -webkit-box-shadow:0 1px 6px rgba(0,0,0,.2);
        box-shadow:0 1px 6px rgba(0,0,0,.2);
    }
    .time-head {
        background-color: #2b85e4;
        color: white;
        font-size: 13px;
        height: 32px;
        line-height: 32px;
        text-align: center;
        border-bottom: 1px solid #e8eaec
    }
</style>