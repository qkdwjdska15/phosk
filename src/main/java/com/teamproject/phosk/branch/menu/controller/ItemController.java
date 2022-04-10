package com.teamproject.phosk.branch.menu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamproject.phosk.branch.menu.service.ItemService;
import com.teamproject.phosk.branch.menu.vo.BranchItemInfo;
import com.teamproject.phosk.branch.menu.vo.CategoryVO;
import com.teamproject.phosk.branch.menu.vo.ItemVO;
import com.teamproject.phosk.branch.menu.vo.NowPage;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/test/*")
@AllArgsConstructor
public class ItemController {

	@Autowired
	private ItemService testService;

	@GetMapping("/test")
	public void test(BranchItemInfo itemInfo, String branch_num, Model model, HttpServletRequest request, NowPage nowPage) {
		log.info("test List .....");

		List<CategoryVO> cateList = testService.cateList(branch_num);
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateTest", testService.testquery(itemInfo));
		model.addAttribute("Options", testService.getBOption(itemInfo));

	}
	@PostMapping("/testUpdate")
	public String updateTest(BranchItemInfo itemInfo, String testArr) {
		System.out.println(itemInfo);
		System.out.println(testArr);
		String[] ArrStr = testArr.split(",");
		System.out.println(ArrStr[0]);
		System.out.println(ArrStr[1]);
		System.out.println(ArrStr[2]);
		System.out.println(ArrStr[3]);
		/* 테스트 하는 이유는 입력부분에서 하나의 문자열로 받와서 배열로 나눈후 입렵하는 방식을 사용할 예정 */
		
		return "redirect:/test/test?branch_num=123-45-67890&category_num=2&item_num=1";
	}
	@PostMapping("/testDel")
	public String testDel() {

		
		return "redirect:/test/test?branch_num=123-45-67890&category_num=2&item_num=1";
	}
	
	@GetMapping("/cateList")
	public void cateList(String branch_num, String cateTest, Model model, HttpServletRequest request, NowPage nowPage) {
		log.info("cate List .....");
		System.out.println(cateTest);
		List<CategoryVO> cateList = testService.cateList(branch_num);
		if (cateTest != null) {
			nowPage.setNowCate(Integer.parseInt(cateTest));
			model.addAttribute("nowPage", nowPage);
		}
		model.addAttribute("cateTest", testService.getMenue(cateTest));
		model.addAttribute("cateList", cateList);

	}

	@GetMapping("/menueManage") /* 카테고리 숫자 받아서 반환할거 필요할거같음 */
	public void menueManage(String branch_num, Model model, HttpServletRequest request, NowPage nowPage) {
		log.info("menuManage List .....");
		String cateTest = request.getParameter("cateTest");
		if (cateTest != null) {
			nowPage.setNowCate(Integer.parseInt(cateTest));
		}
		List<CategoryVO> cateList = testService.cateList(branch_num);
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateTest", testService.menuGetAll(cateTest));
		model.addAttribute("nowPage", nowPage);
	}

	@GetMapping("/detailInfo")
	public void detailInfo(String menue_name, Model model) {
		model.addAttribute("meList", testService.detailInfo(menue_name));
	}
	
	@PostMapping("/modify")
	public String menueModify(ItemVO menueVO, RedirectAttributes rttr, NowPage nowPage) {
		testService.modify(menueVO);
		int nowCate = nowPage.getNowCate();
		rttr.addFlashAttribute("result", "modify success");
		return "redirect:/test/menueManage" + nowCate;
	}

	@PostMapping("/delete")
	public String menueDelete(ItemVO menueVO, RedirectAttributes rttr, NowPage nowPage) {
		testService.delete(menueVO);
		int nowCate = nowPage.getNowCate();
		rttr.addFlashAttribute("result", "delete success");
		return "redirect:/test/menueManage" + nowCate;
	}

	@GetMapping("/insertMenue")
	public void insertMenue(String branch_num, Model model, NowPage nowPage) {
		log.info("insertMenue List .....");
		List<CategoryVO> cateList = testService.cateList(branch_num);
		model.addAttribute("cateList", cateList);
		model.addAttribute("nowPage", nowPage);
	}

	@PostMapping("/insertMenue")
	public String insertMenue(ItemVO menueVO, int cateTest, RedirectAttributes rttr, NowPage nowPage) {
		testService.insert(menueVO);
		rttr.addFlashAttribute("result", "insert success");
		return "redirect:/test/menueManage?cateTest=" + cateTest;
	}

	@PostMapping("/insrtCategory")
	public String insrtCategory(CategoryVO categoryVO, int nowCate , RedirectAttributes rttr, NowPage nowPage) {
		testService.insrtCategory(categoryVO);
		return "redirect:/test/menueManage?cateTest=" + nowCate;
	}
	
	@PostMapping("/updateCateName")
	public String updateCateName(int category_num, CategoryVO cateVO, RedirectAttributes rttr) {
		testService.updateCateName(cateVO);
		rttr.addFlashAttribute("result", "UpCateName success");
		return "redirect:/test/menueManage?cateTest=" + category_num;
	}
	
	
	@PostMapping("/deleteCategory")
	public String deleteCategory(CategoryVO cateVO, RedirectAttributes rttr) {
		testService.deleteCategory(cateVO);
		rttr.addFlashAttribute("result", "delCate success");
		return "redirect:/test/menueManage";
	}
	
	@PostMapping("/deleteBestMenu")
	public String deleteBestMenu(HttpServletRequest request, RedirectAttributes rttr, NowPage nowPage) {
		String[] ajaxData = request.getParameterValues("checkedbtn");
		int nowCate = nowPage.getNowCate();
		for (int i = 0; i < ajaxData.length; i++) {
			testService.delBestMenu(ajaxData[i]);
		}
		rttr.addFlashAttribute("result", "deleteBestMenu success");
		return "redirect:/test/menueManage?cateTest="+ nowCate;
	}
	
	@PostMapping("/deleteChk")
	public String menueDelete(HttpServletRequest request, RedirectAttributes rttr, NowPage nowPage) {
		String[] ajaxData = request.getParameterValues("checkedbtn");
		int nowCate = nowPage.getNowCate();
		for (int i = 0; i < ajaxData.length; i++) {
			testService.chkDel(ajaxData[i]);
		}
		rttr.addFlashAttribute("result", "deleteChk success");
		return "redirect:/test/menueManage?cateTest="+ nowCate;
	}
	
	@PostMapping("/addBestMenu")
	public String addBestMenu(HttpServletRequest request, RedirectAttributes rttr, NowPage nowPage) {
		String[] ajaxData = request.getParameterValues("checkedbtn");
		int nowCate = nowPage.getNowCate();
		for (int i = 0; i < ajaxData.length; i++) {
			testService.addBestMenu(ajaxData[i]);
			System.out.println(ajaxData[i]);
		}
		rttr.addFlashAttribute("result", "deleteChk success");
		return "redirect:/test/menueManage?cateTest="+ nowCate;
	}
	
	
	
}
