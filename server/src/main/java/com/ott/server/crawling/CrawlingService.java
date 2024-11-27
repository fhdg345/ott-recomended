package com.ott.server.crawling;

import com.ott.server.media.dto.MediaDto;
import com.ott.server.media.service.MediaService;
import com.ott.server.mediaott.entity.MediaOtt;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

@Service
@Slf4j
public class CrawlingService {

    private final MediaService mediaService;
    private WebDriver driver;

    private static final String url = "https://www.justwatch.com/kr";

    //private static final String url = "http://127.0.0.1:5500/110.html";

    public CrawlingService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    public int crawlMedias(int location){
        log.info("크롬 드라이버 확인");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");       //팝업안띄움
        //options.addArguments("headless");                       //브라우저 안띄움
        options.addArguments("--disable-gpu");			//gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

        driver = new ChromeDriver(options);


        log.info("크롬 드라이버 생성");


        int lastLocation = -1;
        try {
            lastLocation =    getDataList(location);
            log.error("getDataList() 실행 완료");
        } catch (InterruptedException e){
            log.error("getDataList() 에러 발생");
            e.printStackTrace();

        }

        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        return lastLocation;


    }






    private int getDataList(int location) throws InterruptedException {
        List<String> list = new ArrayList<>();
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(url);
        String originalWindow = driver.getWindowHandle();

        // JavaScript 실행
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('uc_user_interaction', 'true');");
        String storedValue = (String) js.executeScript("return localStorage.getItem('uc_user_interaction');");

        if ("true".equals(storedValue)) {
            System.out.println("localStorage에 값이 제대로 저장되었습니다: " + storedValue);
        } else {
            System.out.println("localStorage 저장 실패 또는 잘못된 값: " + storedValue);
        }

        Thread.sleep(5000);

        int currentLocation = 0;
        int nextLocation = 275;
        for (int i = 1; i < location; i = i + 8) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(" + currentLocation + ", " + nextLocation + ")");
            currentLocation = nextLocation;
            nextLocation += 275;
            Thread.sleep(500);
        }

        outer: while (location < 10000) {
            try {
                WebElement element = driver.findElement(
                        By.xpath("//div[@data-testid='titleItem'][" + location + "]"));
                location++;

                WebElement linkElement = element.findElement(By.tagName("a"));
                String movieUrl = linkElement.getAttribute("href");
                String movieTitle = linkElement.getAttribute("data-title");

                System.out.println("영화 제목: " + movieTitle);
                System.out.println("영화 URL: " + movieUrl);

                //linkElement.click();

                webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@class='price-comparison__grid__row__element']")));

                List<WebElement> ottElements = driver.findElements(By.className("price-comparison__grid__row__element"));
                List<MediaOtt> mediaOtts = new ArrayList<>();
                List<Integer> ottNumber = new ArrayList<>();

                for (int i = 1; i <= ottElements.size(); i++) {
                    try {
                        String ottName = ottElements.get(i - 1)
                                .findElement(By.tagName("img"))
                                .getAttribute("title");

                        if (ottName.equals("wavve") || ottName.equals("Watcha") || ottName.equals("Netflix") || ottName.equals("Disney Plus")) {
                            MediaOtt mediaOtt = new MediaOtt();
                            mediaOtt.setOttName(ottName);
                            mediaOtts.add(mediaOtt);
                            ottNumber.add(i);
                            System.out.println(ottName);
                        }
                    } catch (RuntimeException e) {
                        System.out.println("OTT 정보를 찾을 수 없습니다.");
                    }
                }

                if (mediaOtts.size() == 0) {
                    System.out.println("OTT 정보 없음");
                    driver.navigate().back();
                    continue;
                }

                // 포스터, 장르, 개봉일 등 추가 정보 크롤링 로직 작성
                String mainPoster = driver.findElement(By.xpath("//picture[@class='title-poster__image']//img"))
                        .getAttribute("src");

                String releaseDateText = driver.findElement(By.xpath("//span[contains(@class, 'release-date')]")).getText();
                int releaseDate = Integer.parseInt(releaseDateText.substring(1, 5));

                System.out.println("포스터 URL: " + mainPoster);
                System.out.println("개봉일: " + releaseDate);

                driver.navigate().back();
            } catch (RuntimeException e) {
                location++;
                continue;
            }
        }
        return location;
    }







    private void test(){

        driver.get("http://127.0.0.1:5500/test.html"); // 스프링 부트 애플리케이션의 기본 URL

        // 이름 입력 및 폼 제출
        driver.findElement(By.id("name")).sendKeys("홍길동");
        driver.findElement(By.id("myForm")).submit();

        // 결과 확인
        String greetingText = driver.findElement(By.id("greeting")).getText();

        System.out.println("결과 확인 : " + greetingText);
    }



}
