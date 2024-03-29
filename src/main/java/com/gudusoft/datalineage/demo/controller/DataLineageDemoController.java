package com.gudusoft.datalineage.demo.controller;

import com.gudusoft.datalineage.demo.dto.DataflowRequest;
import com.gudusoft.datalineage.demo.dto.Result;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.dlineage.DataFlowAnalyzer;
import gudusoft.gsqlparser.dlineage.dataflow.model.Option;
import gudusoft.gsqlparser.dlineage.dataflow.model.RelationshipType;
import gudusoft.gsqlparser.dlineage.dataflow.model.xml.dataflow;
import gudusoft.gsqlparser.dlineage.graph.DataFlowGraphGenerator;
import gudusoft.gsqlparser.dlineage.util.DataflowUtility;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/sqlflow")
public class DataLineageDemoController {

    @RequestMapping(value = "/datalineage", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> dataflow(@Valid @RequestBody DataflowRequest req) throws Exception {
        Option option = new Option();
        option.setVendor(EDbVendor.valueOf(req.getDbVendor()));
        option.setSimpleOutput(false);
        option.setIgnoreRecordSet(false);
        option.filterRelationTypes("fdd,fddi,frd,fdr".split(","));
        option.setLinkOrphanColumnToFirstTable(true);
        option.setOutput(false);
        option.setSimpleShowFunction(req.isSimpleShowFunction());
        option.setShowConstantTable(req.isShowConstantTable());
        option.setTransform(req.isShowTransform());
        option.setTransformCoordinate(req.isShowTransform());
        option.setShowCountTableColumn(true);
        if(Objects.nonNull(req.getShowResultSetTypes())){
            option.showResultSetTypes(req.getShowResultSetTypes().split(","));
            option.setIgnoreRecordSet(true);
            option.setSimpleOutput(false);
        }
        DataFlowAnalyzer analyzer = new DataFlowAnalyzer(req.getSqlText(), option);
        analyzer.generateDataFlow(true);
        dataflow dataflow = analyzer.getDataFlow();
        if(req.isIgnoreRecordSet() && Objects.isNull(req.getShowResultSetTypes())){
            analyzer = new DataFlowAnalyzer("", option.getVendor(), false);
            analyzer.setIgnoreRecordSet(req.isIgnoreRecordSet());
            analyzer.getOption().setShowERDiagram(true);
            ArrayList types = new ArrayList();
            types.add(RelationshipType.fdd.name());
            types.add(RelationshipType.fdr.name());
            dataflow = analyzer.getSimpleDataflow(dataflow, false, types);
        }
        if(req.isTableLevel()){
            dataflow = DataflowUtility.convertToTableLevelDataflow(dataflow);
        }
        if(dataflow.getRelationships().size() > 2000){
            return Result.error(500, "More than 2,000 relationships, the front end can not be displayed!");
        }
        DataFlowGraphGenerator generator = new DataFlowGraphGenerator();
        String result = generator.genDlineageGraph(option.getVendor(),req.isIndirect(), dataflow);
        return Result.success(result);
    }

    @RequestMapping(value = "/erdiagram", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> erflow(@Valid @RequestBody DataflowRequest req) throws Exception {
        Option option = new Option();
        option.setVendor(EDbVendor.valueOf(req.getDbVendor()));
        option.setShowERDiagram(true);
        DataFlowAnalyzer analyzer = new DataFlowAnalyzer(req.getSqlText(), option);
        analyzer.generateDataFlow();
        dataflow dataflow = analyzer.getDataFlow();
        DataFlowGraphGenerator generator = new DataFlowGraphGenerator();
        String result = generator.genERGraph(option.getVendor(), dataflow);
        return Result.success(result);
    }

}
