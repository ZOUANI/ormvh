import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { IUser, UserService } from 'app/core';
import { ICourrier } from 'app/shared/model/courrier.model';
import { CourrierService } from 'app/entities/courrier';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html'
})
export class TaskUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  courriers: ICourrier[];
  dateAccuseDp: any;
  dateReponseDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    createdAt: [],
    updatedAt: [],
    assignedAt: [],
    processedAt: [],
    observation: [],
    status: [],
    createdBy: [],
    updatedBy: [],
    delai: [],
    relance: [],
    accuse: [],
    reponse: [],
    dateAccuse: [],
    dateReponse: [],
    assigne: [],
    courrier: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected taskService: TaskService,
    protected userService: UserService,
    protected courrierService: CourrierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ task }) => {
      this.updateForm(task);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.courrierService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICourrier[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourrier[]>) => response.body)
      )
      .subscribe((res: ICourrier[]) => (this.courriers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(task: ITask) {
    this.editForm.patchValue({
      id: task.id,
      title: task.title,
      description: task.description,
      createdAt: task.createdAt != null ? task.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: task.updatedAt != null ? task.updatedAt.format(DATE_TIME_FORMAT) : null,
      assignedAt: task.assignedAt != null ? task.assignedAt.format(DATE_TIME_FORMAT) : null,
      processedAt: task.processedAt != null ? task.processedAt.format(DATE_TIME_FORMAT) : null,
      observation: task.observation,
      status: task.status,
      createdBy: task.createdBy,
      updatedBy: task.updatedBy,
      delai: task.delai,
      relance: task.relance,
      accuse: task.accuse,
      reponse: task.reponse,
      dateAccuse: task.dateAccuse,
      dateReponse: task.dateReponse,
      assigne: task.assigne,
      courrier: task.courrier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      assignedAt:
        this.editForm.get(['assignedAt']).value != null ? moment(this.editForm.get(['assignedAt']).value, DATE_TIME_FORMAT) : undefined,
      processedAt:
        this.editForm.get(['processedAt']).value != null ? moment(this.editForm.get(['processedAt']).value, DATE_TIME_FORMAT) : undefined,
      observation: this.editForm.get(['observation']).value,
      status: this.editForm.get(['status']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value,
      delai: this.editForm.get(['delai']).value,
      relance: this.editForm.get(['relance']).value,
      accuse: this.editForm.get(['accuse']).value,
      reponse: this.editForm.get(['reponse']).value,
      dateAccuse: this.editForm.get(['dateAccuse']).value,
      dateReponse: this.editForm.get(['dateReponse']).value,
      assigne: this.editForm.get(['assigne']).value,
      courrier: this.editForm.get(['courrier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackCourrierById(index: number, item: ICourrier) {
    return item.id;
  }
}
