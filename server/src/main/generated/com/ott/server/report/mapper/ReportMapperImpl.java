package com.ott.server.report.mapper;

import com.ott.server.media.entity.Media;
import com.ott.server.report.dto.ReportDto;
import com.ott.server.report.entity.Report;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T03:53:35+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report reportPostToReport(ReportDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Report report = new Report();

        report.setTitle( requestBody.getTitle() );
        report.setContent( requestBody.getContent() );

        return report;
    }

    @Override
    public Report reportPatchToReport(ReportDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Report report = new Report();

        report.setTitle( requestBody.getTitle() );
        report.setContent( requestBody.getContent() );
        report.setCompletion( requestBody.getCompletion() );

        return report;
    }

    @Override
    public ReportDto.Response reportToReportResponse(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportDto.Response response = new ReportDto.Response();

        Long mediaId = reportMediaMediaId( report );
        if ( mediaId != null ) {
            response.setMediaId( mediaId );
        }
        if ( report.getId() != null ) {
            response.setId( report.getId() );
        }
        response.setTitle( report.getTitle() );
        response.setContent( report.getContent() );
        response.setCompletion( report.getCompletion() );
        response.setCreatedAt( report.getCreatedAt() );
        response.setLastModifiedAt( report.getLastModifiedAt() );

        return response;
    }

    @Override
    public List<ReportDto.Response> reportsToReportResponses(List<Report> reports) {
        if ( reports == null ) {
            return null;
        }

        List<ReportDto.Response> list = new ArrayList<ReportDto.Response>( reports.size() );
        for ( Report report : reports ) {
            list.add( reportToReportResponse( report ) );
        }

        return list;
    }

    private Long reportMediaMediaId(Report report) {
        if ( report == null ) {
            return null;
        }
        Media media = report.getMedia();
        if ( media == null ) {
            return null;
        }
        Long mediaId = media.getMediaId();
        if ( mediaId == null ) {
            return null;
        }
        return mediaId;
    }
}
