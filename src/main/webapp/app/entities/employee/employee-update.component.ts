import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IUser, UserService } from 'app/core';
import { ILeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from 'app/entities/le-service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  leservices: ILeService[];

  editForm = this.fb.group({
    id: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
    user: [],
    service: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeService: EmployeeService,
    protected userService: UserService,
    protected leServiceService: LeServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.leServiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILeService[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILeService[]>) => response.body)
      )
      .subscribe((res: ILeService[]) => (this.leservices = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(employee: IEmployee) {
    this.editForm.patchValue({
      id: employee.id,
      createdAt: employee.createdAt != null ? employee.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: employee.updatedAt != null ? employee.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: employee.createdBy,
      updatedBy: employee.updatedBy,
      user: employee.user,
      service: employee.service
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value,
      user: this.editForm.get(['user']).value,
      service: this.editForm.get(['service']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
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

  trackLeServiceById(index: number, item: ILeService) {
    return item.id;
  }
}
