import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';
import { ExpeditorTypeDeleteDialogComponent } from './expeditor-type-delete-dialog.component';

@Component({
  selector: 'jhi-expeditor-type',
  templateUrl: './expeditor-type.component.html',
})
export class ExpeditorTypeComponent implements OnInit, OnDestroy {
  expeditorTypes?: IExpeditorType[];
  eventSubscriber?: Subscription;

  constructor(
    protected expeditorTypeService: ExpeditorTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.expeditorTypeService.query().subscribe((res: HttpResponse<IExpeditorType[]>) => (this.expeditorTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExpeditorTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExpeditorType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExpeditorTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('expeditorTypeListModification', () => this.loadAll());
  }

  delete(expeditorType: IExpeditorType): void {
    const modalRef = this.modalService.open(ExpeditorTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.expeditorType = expeditorType;
  }
}
