import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICourrier, Courrier } from 'app/shared/model/courrier.model';
import { CourrierService } from './courrier.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IVoie } from 'app/shared/model/voie.model';
import { VoieService } from 'app/entities/voie/voie.service';
import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from 'app/entities/nature-courrier/nature-courrier.service';
import { IExpeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from 'app/entities/expeditor/expeditor.service';
import { ILeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from 'app/entities/le-service/le-service.service';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation/evaluation.service';
import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from 'app/entities/courrier-object/courrier-object.service';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from 'app/entities/expeditor-type/expeditor-type.service';
import { ISubdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from 'app/entities/subdivision/subdivision.service';
import { IBordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from 'app/entities/bordereau/bordereau.service';

type SelectableEntity =
  | IVoie
  | INatureCourrier
  | ICourrier
  | IExpeditor
  | ILeService
  | IEvaluation
  | ICourrierObject
  | IExpeditorType
  | ISubdivision
  | IBordereau;

@Component({
  selector: 'jhi-courrier-update',
  templateUrl: './courrier-update.component.html',
})
export class CourrierUpdateComponent implements OnInit {
  isSaving = false;
  voies: IVoie[] = [];
  naturecourriers: INatureCourrier[] = [];
  linkedtos: ICourrier[] = [];
  expeditors: IExpeditor[] = [];
  leservices: ILeService[] = [];
  evaluations: IEvaluation[] = [];
  courrierobjects: ICourrierObject[] = [];
  expeditortypes: IExpeditorType[] = [];
  subdivisions: ISubdivision[] = [];
  bordereaus: IBordereau[] = [];
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
    bordereau: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
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

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courrier }) => {
      if (!courrier.id) {
        const today = moment().startOf('day');
        courrier.createdAt = today;
        courrier.updatedAt = today;
        courrier.receivedAt = today;
        courrier.sentAt = today;
      }

      this.updateForm(courrier);

      this.voieService
        .query({ filter: 'courrier-is-null' })
        .pipe(
          map((res: HttpResponse<IVoie[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVoie[]) => {
          if (!courrier.voie || !courrier.voie.id) {
            this.voies = resBody;
          } else {
            this.voieService
              .find(courrier.voie.id)
              .pipe(
                map((subRes: HttpResponse<IVoie>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVoie[]) => (this.voies = concatRes));
          }
        });

      this.natureCourrierService
        .query({ filter: 'courrier-is-null' })
        .pipe(
          map((res: HttpResponse<INatureCourrier[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: INatureCourrier[]) => {
          if (!courrier.natureCourrier || !courrier.natureCourrier.id) {
            this.naturecourriers = resBody;
          } else {
            this.natureCourrierService
              .find(courrier.natureCourrier.id)
              .pipe(
                map((subRes: HttpResponse<INatureCourrier>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: INatureCourrier[]) => (this.naturecourriers = concatRes));
          }
        });

      this.courrierService
        .query({ 'courrierId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ICourrier[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICourrier[]) => {
          if (!courrier.linkedTo || !courrier.linkedTo.id) {
            this.linkedtos = resBody;
          } else {
            this.courrierService
              .find(courrier.linkedTo.id)
              .pipe(
                map((subRes: HttpResponse<ICourrier>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICourrier[]) => (this.linkedtos = concatRes));
          }
        });

      this.expeditorService.query().subscribe((res: HttpResponse<IExpeditor[]>) => (this.expeditors = res.body || []));

      this.leServiceService.query().subscribe((res: HttpResponse<ILeService[]>) => (this.leservices = res.body || []));

      this.evaluationService.query().subscribe((res: HttpResponse<IEvaluation[]>) => (this.evaluations = res.body || []));

      this.courrierObjectService.query().subscribe((res: HttpResponse<ICourrierObject[]>) => (this.courrierobjects = res.body || []));

      this.expeditorTypeService.query().subscribe((res: HttpResponse<IExpeditorType[]>) => (this.expeditortypes = res.body || []));

      this.subdivisionService.query().subscribe((res: HttpResponse<ISubdivision[]>) => (this.subdivisions = res.body || []));

      this.bordereauService.query().subscribe((res: HttpResponse<IBordereau[]>) => (this.bordereaus = res.body || []));
    });
  }

  updateForm(courrier: ICourrier): void {
    this.editForm.patchValue({
      id: courrier.id,
      idCourrier: courrier.idCourrier,
      subject: courrier.subject,
      description: courrier.description,
      typeCourrier: courrier.typeCourrier,
      createdAt: courrier.createdAt ? courrier.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: courrier.updatedAt ? courrier.updatedAt.format(DATE_TIME_FORMAT) : null,
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
      receivedAt: courrier.receivedAt ? courrier.receivedAt.format(DATE_TIME_FORMAT) : null,
      instruction: courrier.instruction,
      expediteurDesc: courrier.expediteurDesc,
      sentAt: courrier.sentAt ? courrier.sentAt.format(DATE_TIME_FORMAT) : null,
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
      bordereau: courrier.bordereau,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ormvahApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      idCourrier: this.editForm.get(['idCourrier'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      description: this.editForm.get(['description'])!.value,
      typeCourrier: this.editForm.get(['typeCourrier'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      delai: this.editForm.get(['delai'])!.value,
      relance: this.editForm.get(['relance'])!.value,
      accuse: this.editForm.get(['accuse'])!.value,
      reponse: this.editForm.get(['reponse'])!.value,
      dateAccuse: this.editForm.get(['dateAccuse'])!.value,
      dateReponse: this.editForm.get(['dateReponse'])!.value,
      status: this.editForm.get(['status'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value,
      receivedAt: this.editForm.get(['receivedAt'])!.value ? moment(this.editForm.get(['receivedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      instruction: this.editForm.get(['instruction'])!.value,
      expediteurDesc: this.editForm.get(['expediteurDesc'])!.value,
      sentAt: this.editForm.get(['sentAt'])!.value ? moment(this.editForm.get(['sentAt'])!.value, DATE_TIME_FORMAT) : undefined,
      destinataireDesc: this.editForm.get(['destinataireDesc'])!.value,
      destinataireVille: this.editForm.get(['destinataireVille'])!.value,
      voie: this.editForm.get(['voie'])!.value,
      natureCourrier: this.editForm.get(['natureCourrier'])!.value,
      linkedTo: this.editForm.get(['linkedTo'])!.value,
      expeditor: this.editForm.get(['expeditor'])!.value,
      destinator: this.editForm.get(['destinator'])!.value,
      coordinator: this.editForm.get(['coordinator'])!.value,
      emetteur: this.editForm.get(['emetteur'])!.value,
      evaluation: this.editForm.get(['evaluation'])!.value,
      courrierObject: this.editForm.get(['courrierObject'])!.value,
      expeditorType: this.editForm.get(['expeditorType'])!.value,
      subdivision: this.editForm.get(['subdivision'])!.value,
      services: this.editForm.get(['services'])!.value,
      bordereau: this.editForm.get(['bordereau'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourrier>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: ILeService[], option: ILeService): ILeService {
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
