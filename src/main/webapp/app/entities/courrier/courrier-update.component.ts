import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICourrier, Courrier } from 'app/shared/model/courrier.model';
import { CourrierService } from './courrier.service';
import { IVoie } from 'app/shared/model/voie.model';
import { VoieService } from 'app/entities/voie';
import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from 'app/entities/nature-courrier';
import { IExpeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from 'app/entities/expeditor';
import { ILeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from 'app/entities/le-service';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation';
import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from 'app/entities/courrier-object';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from 'app/entities/expeditor-type';
import { ISubdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from 'app/entities/subdivision';
import { IBordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from 'app/entities/bordereau';

@Component({
  selector: 'jhi-courrier-update',
  templateUrl: './courrier-update.component.html'
})
export class CourrierUpdateComponent implements OnInit {
  isSaving: boolean;

  voies: IVoie[];

  naturecourriers: INatureCourrier[];

  linkedtos: ICourrier[];

  expeditors: IExpeditor[];

  leservices: ILeService[];

  evaluations: IEvaluation[];

  courrierobjects: ICourrierObject[];

  expeditortypes: IExpeditorType[];

  subdivisions: ISubdivision[];

  bordereaus: IBordereau[];
  dateAccuseDp: any;
  dateReponseDp: any;

  editForm = this.fb.group({
    id: [],
    idCourrier: [],
    subject: [],
    description: [],
    typeCourrier: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
    delai: [],
    relance: [],
    accuse: [],
    reponse: [],
    dateAccuse: [],
    dateReponse: [],
    status: [],
    data: [],
    dataContentType: [],
    receivedAt: [],
    instruction: [],
    expediteurDesc: [],
    sentAt: [],
    destinataireDesc: [],
    destinataireVille: [],
    voie: [],
    natureCourrier: [],
    linkedTo: [],
    expeditor: [],
    destinator: [],
    coordinator: [],
    emetteur: [],
    evaluation: [],
    courrierObject: [],
    expeditorType: [],
    subdivision: [],
    services: [],
    bordereau: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected courrierService: CourrierService,
    protected voieService: VoieService,
    protected natureCourrierService: NatureCourrierService,
    protected expeditorService: ExpeditorService,
    protected leServiceService: LeServiceService,
    protected evaluationService: EvaluationService,
    protected courrierObjectService: CourrierObjectService,
    protected expeditorTypeService: ExpeditorTypeService,
    protected subdivisionService: SubdivisionService,
    protected bordereauService: BordereauService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ courrier }) => {
      this.updateForm(courrier);
    });
    this.voieService
      .query({ 'courrierId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVoie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVoie[]>) => response.body)
      )
      .subscribe(
        (res: IVoie[]) => {
          if (!this.editForm.get('voie').value || !this.editForm.get('voie').value.id) {
            this.voies = res;
          } else {
            this.voieService
              .find(this.editForm.get('voie').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVoie>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVoie>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVoie) => (this.voies = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.natureCourrierService
      .query({ 'courrierId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<INatureCourrier[]>) => mayBeOk.ok),
        map((response: HttpResponse<INatureCourrier[]>) => response.body)
      )
      .subscribe(
        (res: INatureCourrier[]) => {
          if (!this.editForm.get('natureCourrier').value || !this.editForm.get('natureCourrier').value.id) {
            this.naturecourriers = res;
          } else {
            this.natureCourrierService
              .find(this.editForm.get('natureCourrier').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<INatureCourrier>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<INatureCourrier>) => subResponse.body)
              )
              .subscribe(
                (subRes: INatureCourrier) => (this.naturecourriers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.courrierService
      .query({ 'courrierId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICourrier[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourrier[]>) => response.body)
      )
      .subscribe(
        (res: ICourrier[]) => {
          if (!this.editForm.get('linkedTo').value || !this.editForm.get('linkedTo').value.id) {
            this.linkedtos = res;
          } else {
            this.courrierService
              .find(this.editForm.get('linkedTo').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICourrier>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICourrier>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICourrier) => (this.linkedtos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.expeditorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IExpeditor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IExpeditor[]>) => response.body)
      )
      .subscribe((res: IExpeditor[]) => (this.expeditors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.leServiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILeService[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILeService[]>) => response.body)
      )
      .subscribe((res: ILeService[]) => (this.leservices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.evaluationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvaluation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvaluation[]>) => response.body)
      )
      .subscribe((res: IEvaluation[]) => (this.evaluations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.courrierObjectService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICourrierObject[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourrierObject[]>) => response.body)
      )
      .subscribe((res: ICourrierObject[]) => (this.courrierobjects = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.expeditorTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IExpeditorType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IExpeditorType[]>) => response.body)
      )
      .subscribe((res: IExpeditorType[]) => (this.expeditortypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.subdivisionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISubdivision[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISubdivision[]>) => response.body)
      )
      .subscribe((res: ISubdivision[]) => (this.subdivisions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bordereauService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBordereau[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBordereau[]>) => response.body)
      )
      .subscribe((res: IBordereau[]) => (this.bordereaus = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(courrier: ICourrier) {
    this.editForm.patchValue({
      id: courrier.id,
      idCourrier: courrier.idCourrier,
      subject: courrier.subject,
      description: courrier.description,
      typeCourrier: courrier.typeCourrier,
      createdAt: courrier.createdAt != null ? courrier.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: courrier.updatedAt != null ? courrier.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: courrier.createdBy,
      updatedBy: courrier.updatedBy,
      delai: courrier.delai,
      relance: courrier.relance,
      accuse: courrier.accuse,
      reponse: courrier.reponse,
      dateAccuse: courrier.dateAccuse,
      dateReponse: courrier.dateReponse,
      status: courrier.status,
      data: courrier.data,
      dataContentType: courrier.dataContentType,
      receivedAt: courrier.receivedAt != null ? courrier.receivedAt.format(DATE_TIME_FORMAT) : null,
      instruction: courrier.instruction,
      expediteurDesc: courrier.expediteurDesc,
      sentAt: courrier.sentAt != null ? courrier.sentAt.format(DATE_TIME_FORMAT) : null,
      destinataireDesc: courrier.destinataireDesc,
      destinataireVille: courrier.destinataireVille,
      voie: courrier.voie,
      natureCourrier: courrier.natureCourrier,
      linkedTo: courrier.linkedTo,
      expeditor: courrier.expeditor,
      destinator: courrier.destinator,
      coordinator: courrier.coordinator,
      emetteur: courrier.emetteur,
      evaluation: courrier.evaluation,
      courrierObject: courrier.courrierObject,
      expeditorType: courrier.expeditorType,
      subdivision: courrier.subdivision,
      services: courrier.services,
      bordereau: courrier.bordereau
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const courrier = this.createFromForm();
    if (courrier.id !== undefined) {
      this.subscribeToSaveResponse(this.courrierService.update(courrier));
    } else {
      this.subscribeToSaveResponse(this.courrierService.create(courrier));
    }
  }

  private createFromForm(): ICourrier {
    return {
      ...new Courrier(),
      id: this.editForm.get(['id']).value,
      idCourrier: this.editForm.get(['idCourrier']).value,
      subject: this.editForm.get(['subject']).value,
      description: this.editForm.get(['description']).value,
      typeCourrier: this.editForm.get(['typeCourrier']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value,
      delai: this.editForm.get(['delai']).value,
      relance: this.editForm.get(['relance']).value,
      accuse: this.editForm.get(['accuse']).value,
      reponse: this.editForm.get(['reponse']).value,
      dateAccuse: this.editForm.get(['dateAccuse']).value,
      dateReponse: this.editForm.get(['dateReponse']).value,
      status: this.editForm.get(['status']).value,
      dataContentType: this.editForm.get(['dataContentType']).value,
      data: this.editForm.get(['data']).value,
      receivedAt:
        this.editForm.get(['receivedAt']).value != null ? moment(this.editForm.get(['receivedAt']).value, DATE_TIME_FORMAT) : undefined,
      instruction: this.editForm.get(['instruction']).value,
      expediteurDesc: this.editForm.get(['expediteurDesc']).value,
      sentAt: this.editForm.get(['sentAt']).value != null ? moment(this.editForm.get(['sentAt']).value, DATE_TIME_FORMAT) : undefined,
      destinataireDesc: this.editForm.get(['destinataireDesc']).value,
      destinataireVille: this.editForm.get(['destinataireVille']).value,
      voie: this.editForm.get(['voie']).value,
      natureCourrier: this.editForm.get(['natureCourrier']).value,
      linkedTo: this.editForm.get(['linkedTo']).value,
      expeditor: this.editForm.get(['expeditor']).value,
      destinator: this.editForm.get(['destinator']).value,
      coordinator: this.editForm.get(['coordinator']).value,
      emetteur: this.editForm.get(['emetteur']).value,
      evaluation: this.editForm.get(['evaluation']).value,
      courrierObject: this.editForm.get(['courrierObject']).value,
      expeditorType: this.editForm.get(['expeditorType']).value,
      subdivision: this.editForm.get(['subdivision']).value,
      services: this.editForm.get(['services']).value,
      bordereau: this.editForm.get(['bordereau']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourrier>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackVoieById(index: number, item: IVoie) {
    return item.id;
  }

  trackNatureCourrierById(index: number, item: INatureCourrier) {
    return item.id;
  }

  trackCourrierById(index: number, item: ICourrier) {
    return item.id;
  }

  trackExpeditorById(index: number, item: IExpeditor) {
    return item.id;
  }

  trackLeServiceById(index: number, item: ILeService) {
    return item.id;
  }

  trackEvaluationById(index: number, item: IEvaluation) {
    return item.id;
  }

  trackCourrierObjectById(index: number, item: ICourrierObject) {
    return item.id;
  }

  trackExpeditorTypeById(index: number, item: IExpeditorType) {
    return item.id;
  }

  trackSubdivisionById(index: number, item: ISubdivision) {
    return item.id;
  }

  trackBordereauById(index: number, item: IBordereau) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
