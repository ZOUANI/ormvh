import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { ExpeditorTypeDetailComponent } from 'app/entities/expeditor-type/expeditor-type-detail.component';
import { ExpeditorType } from 'app/shared/model/expeditor-type.model';

describe('Component Tests', () => {
  describe('ExpeditorType Management Detail Component', () => {
    let comp: ExpeditorTypeDetailComponent;
    let fixture: ComponentFixture<ExpeditorTypeDetailComponent>;
    const route = ({ data: of({ expeditorType: new ExpeditorType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ExpeditorTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpeditorTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpeditorTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load expeditorType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expeditorType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
