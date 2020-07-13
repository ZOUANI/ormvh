import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITask } from 'app/shared/model/task.model';

type EntityResponseType = HttpResponse<ITask>;
type EntityArrayResponseType = HttpResponse<ITask[]>;

@Injectable({ providedIn: 'root' })
export class TaskService {
  public resourceUrl = SERVER_API_URL + 'api/tasks';

  constructor(protected http: HttpClient) {}

  create(task: ITask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(task);
    return this.http
      .post<ITask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(task: ITask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(task);
    return this.http
      .put<ITask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITask[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(task: ITask): ITask {
    const copy: ITask = Object.assign({}, task, {
      createdAt: task.createdAt != null && task.createdAt.isValid() ? task.createdAt.toJSON() : null,
      updatedAt: task.updatedAt != null && task.updatedAt.isValid() ? task.updatedAt.toJSON() : null,
      assignedAt: task.assignedAt != null && task.assignedAt.isValid() ? task.assignedAt.toJSON() : null,
      processedAt: task.processedAt != null && task.processedAt.isValid() ? task.processedAt.toJSON() : null,
      dateAccuse: task.dateAccuse != null && task.dateAccuse.isValid() ? task.dateAccuse.format(DATE_FORMAT) : null,
      dateReponse: task.dateReponse != null && task.dateReponse.isValid() ? task.dateReponse.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
      res.body.assignedAt = res.body.assignedAt != null ? moment(res.body.assignedAt) : null;
      res.body.processedAt = res.body.processedAt != null ? moment(res.body.processedAt) : null;
      res.body.dateAccuse = res.body.dateAccuse != null ? moment(res.body.dateAccuse) : null;
      res.body.dateReponse = res.body.dateReponse != null ? moment(res.body.dateReponse) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((task: ITask) => {
        task.createdAt = task.createdAt != null ? moment(task.createdAt) : null;
        task.updatedAt = task.updatedAt != null ? moment(task.updatedAt) : null;
        task.assignedAt = task.assignedAt != null ? moment(task.assignedAt) : null;
        task.processedAt = task.processedAt != null ? moment(task.processedAt) : null;
        task.dateAccuse = task.dateAccuse != null ? moment(task.dateAccuse) : null;
        task.dateReponse = task.dateReponse != null ? moment(task.dateReponse) : null;
      });
    }
    return res;
  }
}
