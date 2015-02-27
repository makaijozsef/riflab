package hu.bme.mit.riflab.cjwc0f.workflow;

import org.apache.felix.service.command.Descriptor;

import hu.bme.mit.riflab.cjwc0f.interf.DictionaryService;
import hu.bme.mit.riflab.cjwc0f.interf.IAddCommunityPointsService;
import hu.bme.mit.riflab.cjwc0f.interf.IAssignRoomNumberService;
import hu.bme.mit.riflab.cjwc0f.interf.IDetermineAverageService;
import hu.bme.mit.riflab.cjwc0f.interf.IDetermineFinalResultService;
import hu.bme.mit.riflab.cjwc0f.interf.IEnterApplicationDataService;
import hu.bme.mit.riflab.cjwc0f.interf.ISocialInspectionService;
import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;

public class Workflow implements IWorkflow {

	private DictionaryService service;
	private IEnterApplicationDataService edService;
	private IDetermineAverageService daService;
	private ISocialInspectionService siService;
	private IAddCommunityPointsService acService;
	private IAssignRoomNumberService arnService;
	private IDetermineFinalResultService dfrService;

	@Descriptor(value = "Starts the workflow")
	@Override
	public void begin() {
		ApplicationData applicationdata = edService.generate();
		System.out.println("Input data:");
		System.out.println(applicationdata);
		
		ApplicationData calculatedData = daService.calculate(applicationdata);
		System.out.println("Determined average:");
		System.out.println(calculatedData);
		
		SocialResult socialResult = siService.createResult(calculatedData);
		System.out.println("Social result:");
		System.out.println(socialResult);
		
		ApplicationData dataWithCommunityPoints = acService.calculate(calculatedData);
		System.out.println("Average with community points:");
		System.out.println(dataWithCommunityPoints);
		
		ApplicationData studyResult = dataWithCommunityPoints;
		if(dataWithCommunityPoints.getAverage() >= 3){
			studyResult = arnService.assignRoom(dataWithCommunityPoints);
		}
		System.out.println("Study room number assignment");
		System.out.println(studyResult);
		
		ApplicationData finalResult = dfrService.decide(studyResult, socialResult);
		System.out.println("Final result");
		System.out.println(finalResult);
		
	}

	
	public void setDict(DictionaryService service){
		System.out.println("bind");
		this.service = service;
	}
	
	public void unsetDict(DictionaryService service) {		
		if(this.service == service){
			this.service = null;
		}
	}
	
	public void setEnterDataService(IEnterApplicationDataService edService){
		this.edService = edService;
		
	}
	
	public void unsetEnterDataService(IEnterApplicationDataService edService) {		
		if(this.edService == edService){
			this.edService = null;
		}
	}
	
	public void setDetermineAverageService(IDetermineAverageService daService){
		this.daService = daService;
		
	}
	
	public void unsetDetermineAverageService(IDetermineAverageService daService) {		
		if(this.daService == daService){
			this.daService = null;
		}
	}
	
	public void setSocialInspectionService(ISocialInspectionService siService){
		this.siService = siService;
		
	}
	
	public void unsetSocialInspectionService(ISocialInspectionService siService) {		
		if(this.siService == siService){
			this.siService = null;
		}
	}
	
	public void setAddCommunityService(IAddCommunityPointsService acService){
		this.acService = acService;
	}
	
	public void unsetAddCommunityService(IAddCommunityPointsService acService) {		
		if(this.acService == acService){
			this.acService = null;
		}
	}
	
	public void setAssignRoomNumberService(IAssignRoomNumberService arnService){
		this.arnService = arnService;
	}
	
	public void unsetAssignRoomNumberService(IAssignRoomNumberService arnService) {		
		if(this.arnService == arnService){
			this.arnService = null;
		}
	}
	
	public void setFinalResultsService(IDetermineFinalResultService dfrService){
		this.dfrService = dfrService;
	}
	
	public void unsetFinalResultsService(IDetermineFinalResultService dfrService) {		
		if(this.dfrService == dfrService){
			this.dfrService = null;
		}
	}
}
