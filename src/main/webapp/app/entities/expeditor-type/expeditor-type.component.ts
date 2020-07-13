import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { AccountService } from 'app/core';
import { ExpeditorTypeService } from './expeditor-type.service';

@Component({
  selector: 'jhi-expeditor-type',
  templateUrl: './expeditor-type.component.html'
})
export class ExpeditorTypeComponent implements OnInit, OnDestroy {
  expeditorTypes: IExpeditorType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected expeditorTypeService: ExpeditorTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.expeditorTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IExpeditorType[]>) => res.ok),
        map((res: HttpResponse<IExpeditorType[]>) => res.body)
      )
      .subscribe(
        (res: IExpeditorType[]) => {
          this.expeditorTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInExpeditorTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IExpeditorType) {
    return item.id;
  }

  registerChangeInExpeditorTypes() {
    this.eventSubscriber = this.eventManager.subscribe('expeditorTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
