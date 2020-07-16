import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { SubdivisionDetailComponent } from 'app/entities/subdivision/subdivision-detail.component';
import { Subdivision } from 'app/shared/model/subdivision.model';

describe('Component Tests', () => {
  describe('Subdivision Management Detail Component', () => {
    let comp: SubdivisionDetailComponent;
    let fixture: ComponentFixture<SubdivisionDetailComponent>;
    const route = ({ data: of({ subdivision: new Subdivision(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [SubdivisionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubdivisionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubdivisionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subdivision on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subdivision).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
