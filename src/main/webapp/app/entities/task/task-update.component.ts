import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICourrier } from 'app/shared/model/courrier.model';
import { CourrierService } from 'app/entities/courrier/courrier.service';

type SelectableEntity = IUser | ICourrier;

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  courriers: ICourrier[] = [];
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
    courrier: [],
  });

  constructor(
    protected taskService: TaskService,
    protected userService: UserService,
    protected courrierService: CourrierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.createdAt = today;
        task.updatedAt = today;
        task.assignedAt = today;
        task.processedAt = today;
      }

      this.updateForm(task);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.courrierService.query().subscribe((res: HttpResponse<ICourrier[]>) => (this.courriers = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      title: task.title,
      description: task.description,
      createdAt: task.createdAt ? task.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: task.updatedAt ? task.updatedAt.format(DATE_TIME_FORMAT) : null,
      assignedAt: task.assignedAt ? task.assignedAt.format(DATE_TIME_FORMAT) : null,
      processedAt: task.processedAt ? task.processedAt.format(DATE_TIME_FORMAT) : null,
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
      courrier: task.courrier,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      assignedAt: this.editForm.get(['assignedAt'])!.value ? moment(this.editForm.get(['assignedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      processedAt: this.editForm.get(['processedAt'])!.value
        ? moment(this.editForm.get(['processedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      observation: this.editForm.get(['observation'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      delai: this.editForm.get(['delai'])!.value,
      relance: this.editForm.get(['relance'])!.value,
      accuse: this.editForm.get(['accuse'])!.value,
      reponse: this.editForm.get(['reponse'])!.value,
      dateAccuse: this.editForm.get(['dateAccuse'])!.value,
      dateReponse: this.editForm.get(['dateReponse'])!.value,
      assigne: this.editForm.get(['assigne'])!.value,
      courrier: this.editForm.get(['courrier'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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
}
