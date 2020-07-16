import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';
import { NatureCourrierDeleteDialogComponent } from './nature-courrier-delete-dialog.component';

@Component({
  selector: 'jhi-nature-courrier',
  templateUrl: './nature-courrier.component.html',
})
export class NatureCourrierComponent implements OnInit, OnDestroy {
  natureCourriers?: INatureCourrier[];
  eventSubscriber?: Subscription;

  constructor(
    protected natureCourrierService: NatureCourrierService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.natureCourrierService.query().subscribe((res: HttpResponse<INatureCourrier[]>) => (this.natureCourriers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNatureCourriers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INatureCourrier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNatureCourriers(): void {
    this.eventSubscriber = this.eventManager.subscribe('natureCourrierListModification', () => this.loadAll());
  }

  delete(natureCourrier: INatureCourrier): void {
    const modalRef = this.modalService.open(NatureCourrierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.natureCourrier = natureCourrier;
  }
}
