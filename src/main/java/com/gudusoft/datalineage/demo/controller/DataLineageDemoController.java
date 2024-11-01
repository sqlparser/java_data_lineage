package com.gudusoft.datalineage.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gudusoft.datalineage.demo.dto.DataflowRequest;
import com.gudusoft.datalineage.demo.dto.Result;
import com.gudusoft.datalineage.demo.dto.ShowDataLineageRequest;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.dlineage.DataFlowAnalyzer;
import gudusoft.gsqlparser.dlineage.dataflow.model.Option;
import gudusoft.gsqlparser.dlineage.dataflow.model.RelationshipType;
import gudusoft.gsqlparser.dlineage.dataflow.model.json.Dataflow;
import gudusoft.gsqlparser.dlineage.dataflow.model.json.Relationship;
import gudusoft.gsqlparser.dlineage.dataflow.model.json.RelationshipElement;
import gudusoft.gsqlparser.dlineage.dataflow.model.xml.*;
import gudusoft.gsqlparser.dlineage.graph.DataFlowGraphGenerator;
import gudusoft.gsqlparser.dlineage.metadata.*;
import gudusoft.gsqlparser.dlineage.util.DataflowUtility;
import gudusoft.gsqlparser.dlineage.util.ProcessUtility;
import gudusoft.gsqlparser.dlineage.util.XML2Model;
import gudusoft.gsqlparser.sqlenv.TSQLEnv;
import gudusoft.gsqlparser.util.json.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sqlflow")
public class DataLineageDemoController {

    @RequestMapping(value = "/datalineage", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> dataflow(@Valid @RequestBody DataflowRequest req) throws Exception {
        if(req.getSqlText() != null && req.getSqlText().length()>10000){
            return Result.error(500, "You are using the free version, and the SQL request cannot exceed 10,000 characters.");
        }
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
            analyzer = new DataFlowAnalyzer("", option.getVendor(), true);
            ArrayList types = new ArrayList();
            types.add(RelationshipType.fdd.name());
            if(req.isIndirect()){
                types.add(RelationshipType.fdr.name());
            }
            dataflow = analyzer.getSimpleDataflow(dataflow, true, types);
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

    @RequestMapping(value = "/datalineage/json", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> getDataflowJson(@Valid @RequestBody DataflowRequest req) throws Exception {
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
            analyzer = new DataFlowAnalyzer("", option.getVendor(), true);
            ArrayList types = new ArrayList();
            types.add(RelationshipType.fdd.name());
            if(req.isIndirect()){
                types.add(RelationshipType.fdr.name());
            }
            dataflow = analyzer.getSimpleDataflow(dataflow, true, types);
        }
        if(req.isTableLevel()){
            dataflow = DataflowUtility.convertToTableLevelDataflow(dataflow);
        }
        Dataflow model = DataFlowAnalyzer.getSqlflowJSONModel(dataflow, option.getVendor());
        return Result.success(JSON.toJSONString(model));
    }
    @RequestMapping(value = "/datalineage/xml", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> getDataflowXml(@Valid @RequestBody DataflowRequest req) throws Exception {
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
            analyzer = new DataFlowAnalyzer("", option.getVendor(), true);
            ArrayList types = new ArrayList();
            types.add(RelationshipType.fdd.name());
            if(req.isIndirect()){
                types.add(RelationshipType.fdr.name());
            }
            dataflow = analyzer.getSimpleDataflow(dataflow, true, types);
        }
        if(req.isTableLevel()){
            dataflow = DataflowUtility.convertToTableLevelDataflow(dataflow);
        }
        String result = XML2Model.saveXML(dataflow);
        return Result.success(result);
    }

    @RequestMapping(value = "/datalineage/csv", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> getDataflowCsv(@Valid @RequestBody DataflowRequest req) throws Exception {
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
            analyzer = new DataFlowAnalyzer("", option.getVendor(), true);
            ArrayList types = new ArrayList();
            types.add(RelationshipType.fdd.name());
            if(req.isIndirect()){
                types.add(RelationshipType.fdr.name());
            }
            dataflow = analyzer.getSimpleDataflow(dataflow, true, types);
        }
        if(req.isTableLevel()){
            dataflow = DataflowUtility.convertToTableLevelDataflow(dataflow);
            String result = ProcessUtility.generateTableLevelLineageCsv(analyzer, dataflow, req.getDelimiter());
            return Result.success(result);
        }
        else{
            String result = ProcessUtility.generateColumnLevelLineageCsv(analyzer, dataflow, req.getDelimiter());
            return Result.success(result);
        }
    }
    @RequestMapping(value = "/erdiagram", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> erflow(@Valid @RequestBody DataflowRequest req) throws Exception {
        if(req.getSqlText() != null && req.getSqlText().length()>10000){
            return Result.error(500, "You are using the free version, and the SQL request cannot exceed 10,000 characters.");
        }
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

    @RequestMapping(value = "/showDataLineage", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public Result<String> showDataLineage(@Valid @RequestBody ShowDataLineageRequest req)  {
        try{
            if (req.getFormat().equalsIgnoreCase("JSON")){
                dataflow dataflow = generateGraphFromDataflowJson(com.alibaba.fastjson.JSON.parseObject(req.getText()));
                DataFlowGraphGenerator generator = new DataFlowGraphGenerator();
                String result = generator.genDlineageGraph(EDbVendor.valueOf(req.getDbVendor()),true, dataflow);
                return Result.success(result);
            }
            else  if (req.getFormat().equalsIgnoreCase("XML")){
                dataflow dataflow = XML2Model.loadXML(dataflow.class, req.getText());
                DataFlowGraphGenerator generator = new DataFlowGraphGenerator();
                String result = generator.genDlineageGraph(EDbVendor.valueOf(req.getDbVendor()),true, dataflow);
                return Result.success(result);
            }
            else  if (req.getFormat().equalsIgnoreCase("CSV")){
                Option option = new Option();
                option.setVendor(EDbVendor.valueOf(req.getDbVendor()));
                option.setSimpleOutput(false);
                option.setIgnoreRecordSet(false);
                DataFlowAnalyzer analyzer = new DataFlowAnalyzer(req.getText(), option);
                analyzer.generateDataFlow();
                dataflow dataflow = analyzer.getDataFlow();
                DataFlowGraphGenerator generator = new DataFlowGraphGenerator();
                String result = generator.genDlineageGraph(EDbVendor.valueOf(req.getDbVendor()),true, dataflow);
                return Result.success(result);
            }
            else{
                return Result.error(500, "not support format: " + req.getFormat());
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500, "format error!");
        }
    }

    public dataflow generateGraphFromDataflowJson(JSONObject json) {
        Dataflow dataFlow = com.alibaba.fastjson.JSON.toJavaObject(json, Dataflow.class);
        dataflow dataflow = new dataflow();
        List<gudusoft.gsqlparser.dlineage.metadata.Table> tables = new ArrayList<>();

        if (dataFlow.getDbobjs() != null) {
            Sqlflow sqlflow = dataFlow.getDbobjs();
            if (sqlflow.getServers() != null) {
                for (Server server : sqlflow.getServers()) {
                    if (server.isSupportsCatalogs() && server.isSupportsSchemas()) {
                        if (server.getDatabases() == null)
                            continue;
                        for (Database database : server.getDatabases()) {
                            if (database.getSchemas() == null)
                                continue;
                            for (Schema schema : database.getSchemas()) {
                                if (schema.getTables() != null) {
                                    for (Table table : schema.getTables()) {
                                        if (server != null) {
                                            table.setServer(server);
                                        }
                                        if (database.getName() != null) {
                                            table.setParent(database);
                                            //table.setDatabase(database.getName());
                                        }
                                        if (schema.getName() != null) {
                                            schema.setParent(database);
                                            table.setParent(schema);
                                            //table.setSchema(schema.getName());
                                        }
                                        tables.add(table);
                                    }
                                }
                                if (schema.getViews() != null) {
                                    for (Table table : schema.getViews()) {
                                        if (server != null) {
                                            table.setServer(server);
                                        }
                                        if (database.getName() != null) {
                                            table.setParent(database);
                                           // table.setDatabase(database.getName());
                                        }
                                        if (schema.getName() != null) {
                                            schema.setParent(database);
                                            table.setParent(schema);
                                            //table.setSchema(schema.getName());
                                        }
                                        tables.add(table);
                                    }
                                }
                                if (schema.getOthers() != null) {
                                    for (Table table : schema.getOthers()) {
                                        if (server != null) {
                                            table.setServer(server);
                                        }
                                        if (database.getName() != null) {
                                            table.setParent(database);
                                           // table.setDatabase(database.getName());
                                        }
                                        if (schema.getName() != null) {
                                            schema.setParent(database);
                                            table.setParent(schema);
                                           // table.setSchema(schema.getName());
                                        }
                                        tables.add(table);
                                    }
                                }
                            }
                        }
                    } else if (server.isSupportsCatalogs() && server.getDatabases() != null) {
                        for (Database database : server.getDatabases()) {
                            if (database.getTables() != null) {
                                for (Table table : database.getTables()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (database.getName() != null) {
                                        table.setParent(database);
                                        //table.setDatabase(database.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                            if (database.getViews() != null) {
                                for (Table table : database.getViews()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (database.getName() != null) {
                                        table.setParent(database);
                                       // table.setDatabase(database.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                            if (database.getOthers() != null) {
                                for (Table table : database.getOthers()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (database.getName() != null) {
                                        table.setParent(database);
                                       // table.setDatabase(database.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                        }
                    } else if (server.isSupportsSchemas() && server.getSchemas() != null) {
                        for (Schema schema : server.getSchemas()) {
                            if (schema.getTables() != null) {
                                for (Table table : schema.getTables()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (schema.getName() != null) {
                                        table.setParent(schema);
                                       // table.setSchema(schema.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                            if (schema.getViews() != null) {
                                for (Table table : schema.getViews()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (schema.getName() != null) {
                                        table.setParent(schema);
                                       // table.setSchema(schema.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                            if (schema.getOthers() != null) {
                                for (Table table : schema.getOthers()) {
                                    if (server != null) {
                                        table.setServer(server);
                                    }
                                    if (schema.getName() != null) {
                                        table.setParent(schema);
                                        //table.setSchema(schema.getName());
                                    }
                                    tables.add(table);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Table dbObject : tables) {
            if (dbObject.getColumns() == null)
                continue;
            table table = new table();
            table.setId(dbObject.getId());
            table.setCoordinate("[{\"x\":-1,\"y\":-1,\"hashCode\":\"0\"},{\"x\":-1,\"y\":-1,\"hashCode\":\"0\"}]");
            table.setName(dbObject.getDisplayName());
            table.setDisplayName(dbObject.getDisplayName());
            if(dbObject.getDatabase() != null){
                table.setDatabase(dbObject.getDatabase().getName());
            }
            if(dbObject.getSchema() != null){
                table.setSchema(dbObject.getSchema().getName());
            }
            table.setType(dbObject.getType());
            //table.setMore(dbObject.getMore());
            switch (dbObject.getType()) {
                case "table":
                    dataflow.getTables().add(table);
                    break;
                case "view":
                case "materialized view":
                    dataflow.getViews().add(table);
                    break;
                case "resultset":
                    if (table.getSubType() != null) {
                        table.setType(table.getSubType());
                        table.setSubType(null);
                    }
                    dataflow.getResultsets().add(table);
                    break;
                case "variable":
                    dataflow.getVariables().add(table);
                    break;
                case "path":
                    dataflow.getPaths().add(table);
                    break;
                case "stream":
                    dataflow.getStreams().add(table);
                    break;
                case "datasource":
                    dataflow.getDatasources().add(table);
                    break;
                case "stage":
                    dataflow.getStages().add(table);
                    break;
                case "sequence":
                    dataflow.getSequences().add(table);
                    break;
                case "schema":
                    dataflow.getSchemas().add(table);
                    break;
                case "database":
                    dataflow.getDatabases().add(table);
                    break;
                default:
                    dataflow.getResultsets().add(table);
            }

            table.setColumns(new ArrayList<>());
            for (Column columnObject : dbObject.getColumns()) {
                if (columnObject == null) {
                    continue;
                }
                column column = new column();
                column.setId(columnObject.getId());
                column.setCoordinate("[{\"x\":-1,\"y\":-1,\"hashCode\":\"0\"},{\"x\":-1,\"y\":-1,\"hashCode\":\"0\"}]");
                column.setName(columnObject.getName());
                column.setDataType(columnObject.getDataType());
                column.setPrimaryKey(columnObject.isPrimaryKey());
                column.setIndexKey(columnObject.isIndexKey());
                column.setUnqiueKey(columnObject.isUnqiueKey());
                column.setForeignKey(columnObject.isUnqiueKey());
                table.getColumns().add(column);
            }
        }

        dataflow.setRelationships(new ArrayList<>());
        for (Relationship relationshipObject : dataFlow.getRelationships()) {
            if (relationshipObject == null ||
                    relationshipObject.getTarget() == null ||
                    relationshipObject.getSources() == null ||
                    relationshipObject.getSources().length == 0) {
                continue;
            }
            relationship relationship = new relationship();
            relationship.setId(relationshipObject.getId());
            relationship.setType(relationshipObject.getType());
            relationship.setEffectType(relationshipObject.getEffectType());
            relationship.setTimestampMin(relationshipObject.getTimestampMin());
            relationship.setTimestampMax(relationshipObject.getTimestampMax());

            targetColumn targetColumn = new targetColumn();
            targetColumn.setId(relationshipObject.getTarget().getId());
            targetColumn.setParent_name(relationshipObject.getTarget().getParentName());
            targetColumn.setParent_id(relationshipObject.getTarget().getParentId());
            targetColumn.setColumn(relationshipObject.getTarget().getColumn());
            relationship.setTarget(targetColumn);

            relationship.setSources(new ArrayList<>());
            if (relationshipObject.getSources() != null) {
                for (RelationshipElement sourceObject : relationshipObject.getSources()) {
                    sourceColumn sourceColumn = new sourceColumn();
                    sourceColumn.setParent_name(sourceObject.getParentName());
                    sourceColumn.setParent_id(sourceObject.getParentId());
                    sourceColumn.setColumn(sourceObject.getColumn());
                    sourceColumn.setId(sourceObject.getId());
                    relationship.getSources().add(sourceColumn);
                }
            }
            dataflow.getRelationships().add(relationship);
        }
        dataflow.setOrientation(dataFlow.getOrientation());
        return dataflow;
    }
}
