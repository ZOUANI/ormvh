import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILeService, LeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';
import { ICourrier } from 'app/shared/model/courrier.model';
import { CourrierService } from 'app/entities/courrier';

@Component({
  selector: 'jhi-le-service-update',
  templateUrl: './le-service-update.component.html'
})
export class LeServiceUpdateComponent implements OnInit {
  isSaving: boolean;

  courriers: ICourrier[];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected leServiceService: LeServiceService,
    protected courrierService: CourrierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ leService }) => {
      this.updateForm(leService);
    });
    this.courrierService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICourrier[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourrier[]>) => response.body)
      )
      .subscribe((res: ICourrier[]) => (this.courriers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(leService: ILeService) {
    this.editForm.patchValue({
      id: leService.id,
      title: leService.title,
      description: leService.description,
      createdAt: leService.createdAt != null ? leService.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: leService.updatedAt != null ? leService.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: leService.createdBy,
      updatedBy: leService.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const leService = this.createFromForm();
    if (leService.id !== undefined) {
      this.subscribeToSaveResponse(this.leServiceService.update(leService));
    } else {
      this.subscribeToSaveResponse(this.leServiceService.create(leService));
    }
  }

  private createFromForm(): ILeService {
    return {
      ...new LeService(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeService>>) {
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

  trackCourrierById(index: number, item: ICourrier) {
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
