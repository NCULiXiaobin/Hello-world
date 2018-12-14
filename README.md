components: {
    seniorSearch,
    fundSelector,
    stockSelector
  },
  
  
  import fundSelector from '@/views/commons/fund-selector/fund-selector.vue';
import stockSelector from '@/views/commons/stock-selector/index';


<fund-selector v-model="baseQryForm.fundid" multiple>
                        </fund-selector>
						
						
						 <stock-selector v-model="baseQryForm.stkcode" :market="baseQryForm.market"></stock-selector>