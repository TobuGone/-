package com.kapphk.pms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanResource;
import com.kapphk.pms.bean.BeanStandardResource;



/**   
* @ClassName: ResourceFormat    
* @Description: 格式化菜单
* @author XS  
* @date 2015-7-17 上午9:36:22    
*         
*/
public class ResourceFormat {
	
	 private Map<Integer,List<BeanResource>> resourceMap = new HashMap<Integer,List<BeanResource>>();
	 
	 /**   
	* @Title: initResourceMap    
	* @Description:    初始化  resourceMap  key 为  所有的  父节点 ； value  为 父节点下面的所有的子节点 
	* @param @param list
	* @param @return    设定文件    
	* @return Integer    返回类型    
	* @throws    
	*/
	private  Integer initResourceMap (List<BeanResource> list ){
			Integer minParentId = null;
		    if(list != null && list.size() > 0){
		    	for(BeanResource r:list){
		    		Integer parentId = r.getParentId();
		    		 List<BeanResource> child =  resourceMap.get(parentId);
		    		 //如果不存在map中  则添加到map中
		    		 if(child == null){
		    			 child = new ArrayList<BeanResource>();
		    			 child.add(r);
		    			 resourceMap.put(parentId, child);
		    		 }else{
		    		  //如果存在 map中   得到所有 以  parentId 为父节点的  资源
		    			 child.add(r);
		    		 }
		    		 if(minParentId == null){
		    			 minParentId = parentId;
		    		 }else if( minParentId > parentId){
		    			 minParentId = parentId;
		    		 }
		    	}
		    }
		    return minParentId;
	};
	
  /**   
* @Title: resourceFormat    
* @Description:  格式化菜单 
* @param @param data
* @param @param rList
* @param @param state    设定文件    
* @return void    返回类型    
* @throws    
*/
private  void resourceFormat(List<BeanStandardResource> data,List<BeanResource> rList,String state){

    	  if(rList != null && rList.size() > 0){
    		  for(BeanResource r:rList){
    			  BeanStandardResource newBSR = new BeanStandardResource(); 
    			  newBSR.setId(r.getId());
    			  newBSR.setText(r.getResourceName());
        		  Integer pid = r.getId();
        		  List<BeanResource> children =   this.resourceMap.get(pid);
        		  if(children !=null && children.size() > 0){
        			 newBSR.setState(state);
            		 List<BeanStandardResource> bsrChildren = new ArrayList<BeanStandardResource>(); 
            		 newBSR.setChildren(bsrChildren);
            	     resourceFormat(bsrChildren,children,state);  
        		  }else{
        			  newBSR.setUrl(r.getUrl()); 
        		  }
        		  data.add(newBSR);
        	  }
    		  
      }
	}
	
	
	public List<BeanStandardResource> format(List<BeanResource> list,String state){
		Integer minParent = this.initResourceMap(list);
		if(minParent != null && this.resourceMap != null && resourceMap.size() > 1){
			List<BeanResource> originalList = this.resourceMap.get(minParent);
			  List<BeanStandardResource> bsrList = new ArrayList<BeanStandardResource>();
			 this.resourceFormat(bsrList,originalList,state);
			 return bsrList;
		}
		 return null;
	}
	
}
